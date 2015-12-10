package de.fhbingen.epro.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.fhbingen.epro.security.CustomUserDetailsService;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	private PasswordEncoder passwortEncoder = new BCryptPasswordEncoder();
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		UserDetails user = userDetailsService.loadUserByUsername(auth.getPrincipal().toString());
		
		if(!passwortEncoder.matches(auth.getCredentials().toString(), user.getPassword()))
			throw new BadCredentialsException("Invalid credentials");
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

		return token;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Authentication.class.isAssignableFrom(clazz);
	}

}
