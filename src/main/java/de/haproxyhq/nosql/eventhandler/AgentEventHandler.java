package de.haproxyhq.nosql.eventhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import de.haproxyhq.mqtt.client.MqttPublisher;
import de.haproxyhq.nosql.model.Agent;

/**
 * this class handles CRUD events fired by the Agent model 
 *  
 * @author Maximilian Büttner, Jonas Depoix, Johannes Hiemer.
 */
@Component
@RepositoryEventHandler(Agent.class)
public class AgentEventHandler {
	
	@Autowired
	private MqttPublisher configPublisher;
	
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
