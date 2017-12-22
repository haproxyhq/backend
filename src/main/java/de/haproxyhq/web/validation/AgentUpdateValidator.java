package de.haproxyhq.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.haproxyhq.mqtt.client.AmqpPublisher;
import de.haproxyhq.nosql.model.Agent;
import de.haproxyhq.nosql.repositories.AgentRepository;
import de.haproxyhq.web.validation.utils.ValidationUtils;

/**
 * 
 * @author Jonas Depoix.
 */
@Component
public class AgentUpdateValidator implements Validator {
	
	@Autowired
	AgentRepository agentRepository;
	
	@Autowired
	AmqpPublisher configPublisher;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Agent.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectBlank(errors, "configTimestamp", "field.required");
	}
}
