package de.haproxyhq.nosql.eventhandler;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import de.haproxyhq.mqtt.client.MqttPublisher;
import de.haproxyhq.nosql.model.Agent;
import de.haproxyhq.nosql.repositories.AgentRepository;
import de.haproxyhq.security.token.TokenUtil;
import de.haproxyhq.utils.AgentUtils;

/**
 * this class handles CRUD events fired by the Agent model 
 *  
 * @author Maximilian Büttner, jdepoix
 */
@Component
@RepositoryEventHandler(Agent.class)
public class AgentEventHandler {
	/**
	 * MQTT client, used to publish the new config to the agents
	 */
	@Autowired
	private MqttPublisher configPublisher;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@Autowired
	private AgentRepository agentRepository;
	
	/**
	 * create the infinite auth token for the agent and save it
	 * @param agent
	 */
	@HandleAfterCreate
	public void createAuthToken(Agent agent) {
		String token = "";
		try {
			token = tokenUtil.createInfiniteAuthToken(AgentUtils.agentPrefix + agent.getId());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		agent.setAuthToken(token);
		agentRepository.save(agent);
	}
	
	/**
	 * publishes the new config to the agent
	 * 
	 * @param agent
	 */
	@HandleAfterSave
	public void publishConfig(Agent agent) {
		configPublisher.publishAgentConfig(agent.getId());
	}
}
