/**
 * 
 */
package de.haproxyhq.config.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author Johannes Hiemer.
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String token = "83u8989";
		//Token token = tokenRepository.getByKey(authentication.getPrincipal().toString());
		
		if (token == null) {
			throw new UsernameNotFoundException(String.format("Invalid credentials", authentication.getPrincipal()));
		}
		
        if(authentication.getCredentials() != null  && !token.equals(authentication.getCredentials().toString())){
            throw new BadCredentialsException("Invalid credentials");
        }
 
        return authentication;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UsernamePasswordAuthenticationToken.class);
	}

}
