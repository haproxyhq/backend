package de.haproxyhq.nosql.eventhandler;

import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import de.haproxyhq.mqtt.client.AmqpPublisher;
import de.haproxyhq.nosql.model.Agent;

/**
 * this class handles CRUD events fired by the Agent model
 * 
 * @author Maximilian Büttner, Jonas Depoix, Johannes Hiemer.
 */
@Component
@RepositoryEventHandler(Agent.class)
public class AgentEventHandler {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AmqpPublisher configPublisher;

	/**
	 * publishes the new config to the agent
	 * 
	 * @param agent
	 */
	@HandleAfterSave
	public void publishConfig(Agent agent) {
		try {
			configPublisher.publishAgentConfig(agent.getId());
		} catch (IllegalStateException | TimeoutException e) {
			log.error(e.getMessage(), e);
		}
	}
}
