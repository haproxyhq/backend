package de.haproxyhq.mqtt.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import de.haproxyhq.mqtt.config.CustomMqttConfig;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@Component
public class MqttPublisher {
	
	@Autowired
	CustomMqttConfig mqttConfig;
	
	@Autowired
	MessageHandler mqttOutbound;
	
	/**
	 * sends the message pull config to the channel /haproxyhq/agents/{agentId}
	 * @param agentId
	 */
	public void publishAgentConfig(String agentId) {
		Message<String> message = MessageBuilder.withPayload("pull config")
				.setHeader(MqttHeaders.TOPIC, mqttConfig.getMqttTopicPrefix() + agentId).build();
		mqttOutbound.handleMessage(message);
	}
}
