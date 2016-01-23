package de.haproxyhq.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.haproxyhq.nosql.model.Agent;
import de.haproxyhq.nosql.repositories.AgentRepository;
import de.haproxyhq.web.validation.utils.ValidationUtils;

/**
 * this validator checks if a update to a config is actually newer and rejects the update in case it isn't
 * 
 * @author jdepoix
 */
@Component
public class AgentUpdateValidator implements Validator {
	@Autowired
	AgentRepository agentRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Agent.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectBlank(errors, "configTimestamp", "timestamp for the config is required");
		Agent newAgent = (Agent) target;
		if(newAgent.getConfigTimestamp() < agentRepository.findOne(newAgent.getId()).getConfigTimestamp()) {
			errors.rejectValue("configTimestamp", "config has already been outdated");
		}
	}
}
