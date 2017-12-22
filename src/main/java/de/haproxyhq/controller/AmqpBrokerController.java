package de.haproxyhq.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.haproxyhq.config.amqp.CustomAmqpConfig;

/**
 * 
 * @author Jonas Depoix.
 *
 */
@Controller
public class AmqpBrokerController {

	@Autowired
	private CustomAmqpConfig mqttConfig;

	@RequestMapping(value = "/amqp/broker", method = RequestMethod.GET)
	public ResponseEntity<Map<String, String>> getMqttBrokerInfo() {
		Map<String, String> mqttBroker = new HashMap<>();
		mqttBroker.put("host", mqttConfig.getHost());
		// mqttBroker.put("clientId", mqttConfig.getClientId());
		mqttBroker.put("exchangePrefix", mqttConfig.getExchangePrefix());

		return new ResponseEntity<Map<String, String>>(mqttBroker, HttpStatus.OK);
	}
}
