package de.haproxyhq.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.haproxyhq.mqtt.config.CustomMqttConfig;

@Controller
public class MqttBrokerController {

	@Autowired
	CustomMqttConfig mqttConfig;

	@RequestMapping(value = "/mqtt/broker", method = RequestMethod.GET)
	public ResponseEntity<Map<String, String>> getMqttBrokerInfo() {
		Map<String, String> mqttBroker = new HashMap<>();
		mqttBroker.put("host", mqttConfig.getMqttHost());
		mqttBroker.put("clientId", mqttConfig.getMqttClientId());
		mqttBroker.put("topicPrefix", mqttConfig.getMqttTopicPrefix());

		return new ResponseEntity<Map<String, String>>(mqttBroker, HttpStatus.OK);
	}
}
