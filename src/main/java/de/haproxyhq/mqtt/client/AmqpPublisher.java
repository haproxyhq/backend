package de.haproxyhq.mqtt.client;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.bson.types.ObjectId;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import de.haproxyhq.config.amqp.CustomAmqpConfig;

/**
 * 
 * @author Christian Brinker.
 *
 */
@Component
public class AmqpPublisher {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private CustomAmqpConfig config;

	@Value("${agent.pull.message.default:pull config}")
	private String defaultPullMessage;

	public void publishAgentConfig(ObjectId agentId) throws IllegalStateException, TimeoutException {
		final String agentIdAsString = agentId.toString();
		String exchange = config.getExchangePrefix() + agentIdAsString;
		String routingKey = config.getRoutingKeyPrefix() + agentIdAsString;
		sendMessage(exchange, routingKey, defaultPullMessage);
	}

	private void sendMessage(String exchange, String routingKey, String payload)
			throws TimeoutException, IllegalStateException {

		Message message = createMessage(payload);

		Message response = this.rabbitTemplate.sendAndReceive(exchange, routingKey, message);

		if (response == null) {
			throw new TimeoutException("Job is taking too long!");
		}

		validateResponse(response);
	}

	protected void validateResponse(Message response) throws IllegalStateException {
		String code = new String(response.getBody());
		if (!Objects.equals(code, "OK")) {
			throw new IllegalStateException("An error occured during informing of HaProxyAgent! Return code was '"
					+ code + "' instead of 'OK'.");
		}
	}

	private Message createMessage(String payload) {
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentEncoding(config.getCharset());
		messageProperties.setContentType(config.getContentType());
		messageProperties.setCorrelationId(newCorrelationId());
		return new Message(payload.getBytes(), messageProperties);
	}

	protected byte[] newCorrelationId() {
		return UUID.randomUUID().toString().getBytes();
	}
}
