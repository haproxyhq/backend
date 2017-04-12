package de.haproxyhq.controller.schema;

import java.util.Date;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.haproxyhq.controller.schema.types.ConnectionDetails;
import de.haproxyhq.controller.schema.types.InternalConnectionDetails;
import de.haproxyhq.mqtt.client.AmqpPublisher;
import de.haproxyhq.nosql.model.Agent;
import de.haproxyhq.nosql.model.HAProxyConfig;
import de.haproxyhq.nosql.repositories.AgentRepository;
import de.haproxyhq.utils.HAProxySectionHandler;
import de.haproxyhq.utils.ResponseMessage;

/**
 * 
 * @author Johannes Hiemer.
 *
 */
@Controller
@RequestMapping(value = "/agents")
public class HAProxySectionConfigurerController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AmqpPublisher mqttPublisher;

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private HAProxySectionHandler haProxySectionHandler;

	@RequestMapping(value = "/{agent}/schemas", method = RequestMethod.PUT)
	public ResponseEntity<Resource<Object>> appendListenerSection(@PathVariable("agent") String agent,
			@RequestParam("type") String type, @RequestBody InternalConnectionDetails internalConnectionDetails,
			HttpServletRequest request, HttpServletResponse response) {

		if (agent == null) {
			return new ResponseEntity<Resource<Object>>(
					new Resource<Object>(new ResponseMessage("No agent name provided.")), HttpStatus.NOT_FOUND);
		}

		ObjectId agentId;
		try {
			agentId = new ObjectId(agent);
		} catch (IllegalArgumentException ex) {
			return new ResponseEntity<Resource<Object>>(
					new Resource<Object>(new ResponseMessage("Agent name is not of type id.")), HttpStatus.NOT_FOUND);
		}
		Agent defaultAgent = agentRepository.findOne(agentId);
		if (defaultAgent != null) {
			HAProxyConfig haProxyConfig = defaultAgent.getHaProxyConfig();

			if (!haProxySectionHandler.exists(haProxyConfig, internalConnectionDetails)) {
				ConnectionDetails externalConnectionDetails = haProxySectionHandler.append(haProxyConfig,
						internalConnectionDetails);
				defaultAgent.setHaProxyConfig(haProxyConfig);

				defaultAgent.setConfigTimestamp(new Date().getTime());
				agentRepository.save(defaultAgent);

				try {
					mqttPublisher.publishAgentConfig(defaultAgent.getId());
				} catch (IllegalStateException | TimeoutException e) {
					log.error(e.getMessage(), e);
				}

				return new ResponseEntity<Resource<Object>>(new Resource<Object>(externalConnectionDetails),
						HttpStatus.CREATED);
			} else
				return new ResponseEntity<Resource<Object>>(
						new Resource<Object>(
								new ResponseMessage("Configuration Entry already exists in HAProxy config")),
						HttpStatus.BAD_REQUEST);

		} else
			return new ResponseEntity<Resource<Object>>(
					new Resource<Object>(new ResponseMessage("Could not find agent for name: " + agent)),
					HttpStatus.NOT_FOUND);

	}

	@RequestMapping(value = "/{agent}/schemas", method = RequestMethod.DELETE)
	public ResponseEntity<Resource<Object>> removeListenerSection(@PathVariable("agent") String agent,
			@RequestParam("type") String type, @RequestBody InternalConnectionDetails internalConnectionDetails,
			HttpServletRequest request, HttpServletResponse response) {

			ObjectId agentId = new ObjectId(agent);
		
			Agent defaultAgent = agentRepository.findOne(agentId);
			if (defaultAgent != null) {
				HAProxyConfig haProxyConfig = defaultAgent.getHaProxyConfig();

				if (haProxySectionHandler.exists(haProxyConfig, internalConnectionDetails)) {
					haProxySectionHandler.remove(haProxyConfig, internalConnectionDetails);
					defaultAgent.setHaProxyConfig(haProxyConfig);

					defaultAgent.setConfigTimestamp(new Date().getTime());
					agentRepository.save(defaultAgent);

					try {
						mqttPublisher.publishAgentConfig(defaultAgent.getId());
					} catch (IllegalStateException | TimeoutException e) {
						log.error(e.getMessage(), e);
					}

					return new ResponseEntity<Resource<Object>>(HttpStatus.NO_CONTENT);
				} else
					return new ResponseEntity<Resource<Object>>(
							new Resource<Object>(new ResponseMessage("Could not find entry in HA Proxy Config")),
							HttpStatus.NOT_FOUND);
			} else
				return new ResponseEntity<Resource<Object>>(
						new Resource<Object>(new ResponseMessage("Could not Agent for Identifier")),
						HttpStatus.NOT_FOUND);
	}
}
