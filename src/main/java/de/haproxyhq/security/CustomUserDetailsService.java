package de.haproxyhq.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.haproxyhq.sql.model.User;
import de.haproxyhq.sql.repositories.UserRepository;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByEmail(username);
		if(user == null) throw new UsernameNotFoundException(String.format("User {0} not found", username));
		return user;
	}

}
