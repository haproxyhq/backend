package de.haproxyhq.mqtt.client;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

/**
 * this class is a light MQTT client, with the only purpose of informing the HAProxyHQ/Agents
 * about their new configs.
 * 
 * @author jdepoix
 */
@Component
public class ConfigPublisher {
	/**
	 * the MQTTClient
	 */
	private MqttClient mqttClient;
	
	/**
	 * Initializes the MQTT Client, and connects to the MQTT broker
	 */
	public ConfigPublisher() {
		try {
			this.mqttClient = new MqttClient("tcp://localhost", "haproxyhq/backend");
			this.mqttClient.connect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * this publishes a mqtt signal to the given agent, which will then retrieve the new config
	 * 
	 * @param agentId the ID of the agent
	 */
	public void publishAgentConfig(String agentId) {
		try {
			if(this.mqttClient.isConnected()) {
				this.mqttClient.publish("/haproxyhq/agents/" + agentId, new MqttMessage());
			}
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * disconnects the MQTT client
	 */
	@PreDestroy
    public void disconnect() {
        try {
        	if(this.mqttClient.isConnected()) {
        		this.mqttClient.disconnect();
        	}
		} catch (MqttException e) {
			e.printStackTrace();
		}
    }
}
