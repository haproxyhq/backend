package de.haproxyhq.mqtt.client;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import de.haproxyhq.config.mqtt.CustomMqttConfig;

/**
 * 
 * @author Maximilian Büttner, Johannes Hiemer.
 *
 */
@Component
public class MqttPublisher {
	
	@Autowired
	private CustomMqttConfig mqttConfig;
	
	@Autowired
	private MessageHandler mqttOutbound;
	
	public void publishAgentConfig(ObjectId agentId) {
		Message<String> message = MessageBuilder.withPayload("pull config")
				.setHeader(MqttHeaders.TOPIC, mqttConfig.getMqttTopicPrefix() + agentId.toString()).build();
		mqttOutbound.handleMessage(message);
	}
}
