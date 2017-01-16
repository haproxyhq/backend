/**
 * 
 */
package de.haproxyhq.config.amqp;

import java.nio.charset.Charset;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Christian Brinker, evoila.
 *
 */
@Configuration
public class CustomAmqpConfig {

	@Value("${rabbitmq.routingkey.prefix}")
	private String routingKeyPrefix;

	@Value("${rabbitmq.exchange.prefix}")
	private String exchangePrefix;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange(exchangePrefix, false, false);
	}

	@PostConstruct
	public void configureRabbitTemplate() {
		rabbitTemplate.setReplyTimeout(120000);
		rabbitTemplate.setCorrelationKey(UUID.randomUUID().toString());
	}

	/**
	 * @return
	 */
	public String getCharset() {
		return Charset.defaultCharset().displayName();
	}

	/**
	 * @return
	 */
	public String getContentType() {
		return MessageProperties.CONTENT_TYPE_JSON;
	}

	/**
	 * @return
	 */
	public String getRoutingKeyPrefix() {
		return routingKeyPrefix;
	}

	/**
	 * @return
	 */
	public String getExchangePrefix() {
		return exchangePrefix;
	}

	/**
	 * @return
	 */
	public String getHost() {
		return rabbitTemplate.getConnectionFactory().getHost();
	}
}
