package de.haproxyhq.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.haproxyhq.nosql.model.Agent;
import de.haproxyhq.nosql.repositories.AgentRepository;
import de.haproxyhq.utils.AgentUtils;

@Service
public class CustomAgentDetailsService implements UserDetailsService {

	@Autowired
	private AgentRepository agentRepository;
	
	@Override
	public UserDetails loadUserByUsername(String prefixedId) throws UsernameNotFoundException {
		String id = prefixedId.substring(AgentUtils.agentPrefix.length());
		Agent agent = agentRepository.findOne(id);
		if(agent == null) throw new UsernameNotFoundException(String.format("Agent {0} not found", agent));
		return agent;
	}
}
