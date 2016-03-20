package de.haproxyhq.mqtt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * 
 * @author Maximilian Büttner, Johannes Hiemer.
 *
 */
@Configuration
public class CustomMqttConfig {

	@Value("${mqtt.host}")
	private String mqttHost;

	@Value("${mqtt.client.id}")
	private String mqttClientId;

	@Value("${mqtt.topic.prefix}")
	private String mqttTopicPrefix;

	@Bean
	public MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		factory.setServerURIs(mqttHost);
		return factory;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttOutboundChannel")
	public MessageHandler mqttOutbound() {
		MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(mqttClientId, mqttClientFactory());
		messageHandler.setAsync(true);
		messageHandler.setDefaultTopic(mqttTopicPrefix + "0");
		return messageHandler;
	}

	@Bean
	public MessageChannel mqttOutboundChannel() {
		return new DirectChannel();
	}

	
	public String getMqttTopicPrefix() {
		return this.mqttTopicPrefix;
	}
	
	public String getMqttHost() {
		return this.mqttHost;
	}

	public String getMqttClientId() {
		return this.mqttClientId;
	}

}
