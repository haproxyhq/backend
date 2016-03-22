package de.haproxyhq.controller.schema;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import de.haproxyhq.mqtt.client.MqttPublisher;
import de.haproxyhq.nosql.model.Agent;
import de.haproxyhq.nosql.model.HaProxyConfig;
import de.haproxyhq.nosql.repositories.AgentRepository;
import de.haproxyhq.utils.HaProxySectionHandler;

/**
 * 
 * @author Johannes Hiemer.
 *
 */
@Controller
@RequestMapping(value = "/agents")
public class HaProxySectionConfigurerController {
	
	private String DEFAULT_AGENT_IDENTIFIER = "Default-HaProxy-Agent";
	
	@Autowired
	private MqttPublisher mqttPublisher;
	
	@Autowired
	private AgentRepository agentRepository;
	
	@Autowired
	private HaProxySectionHandler haPoxySectionHandler;

	@RequestMapping(value = "/{agent}/schemas/append", method = RequestMethod.PUT)
	public ResponseEntity<Resource<Object>> appendListenerSection(@PathVariable("agent") String agent, 
			@RequestParam("type") String type, @RequestBody ConnectionDetails connectionDetails,
			HttpServletRequest request, HttpServletResponse response) {

		if (agent.equals(DEFAULT_AGENT_IDENTIFIER)) {
			Agent defaultAgent = agentRepository.findByName(DEFAULT_AGENT_IDENTIFIER);
			if (defaultAgent != null) {
				HaProxyConfig haProxyConfig = defaultAgent.getHaProxyConfig();
				
				if (!haPoxySectionHandler.exists(haProxyConfig, connectionDetails)) {
					haPoxySectionHandler.append(haProxyConfig, connectionDetails);
					defaultAgent.setHaProxyConfig(haProxyConfig);
					
					defaultAgent.setConfigTimestamp(new Date().getTime());
					agentRepository.save(defaultAgent);

					mqttPublisher.publishAgentConfig(defaultAgent.getId());
				} else
					return new ResponseEntity<Resource<Object>>(HttpStatus.BAD_REQUEST);
				
			} else
				return new ResponseEntity<Resource<Object>>(HttpStatus.NOT_FOUND);
		} else 
			return new ResponseEntity<Resource<Object>>(HttpStatus.NOT_FOUND);	
		
		
		return new ResponseEntity<Resource<Object>>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{agent}/schemas/remove", method = RequestMethod.GET)
	public ResponseEntity<Resource<Object>> removeListenerSection(@PathVariable("agent") String agent, 
			@RequestParam("type") String type, @RequestBody ConnectionDetails connectionDetails,
			HttpServletRequest request, HttpServletResponse response) {

		if (agent.equals(DEFAULT_AGENT_IDENTIFIER)) {
			Agent defaultAgent = agentRepository.findByName(DEFAULT_AGENT_IDENTIFIER);
			if (defaultAgent != null) {
				HaProxyConfig haProxyConfig = defaultAgent.getHaProxyConfig();
				
				if (!haPoxySectionHandler.exists(haProxyConfig, connectionDetails)) {
					haPoxySectionHandler.remove(haProxyConfig, connectionDetails);	
				
					agentRepository.save(defaultAgent);
				
					mqttPublisher.publishAgentConfig(defaultAgent.getId());
				} else
					return new ResponseEntity<Resource<Object>>(HttpStatus.NOT_FOUND);
			} else
				return new ResponseEntity<Resource<Object>>(HttpStatus.NOT_FOUND);
		} else 
			return new ResponseEntity<Resource<Object>>(HttpStatus.NOT_FOUND);	
		
		
		return new ResponseEntity<Resource<Object>>(HttpStatus.CREATED);
	}
}
