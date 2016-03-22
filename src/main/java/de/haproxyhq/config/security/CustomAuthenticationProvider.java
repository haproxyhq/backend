/**
 * 
 */
package de.haproxyhq.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import de.haproxyhq.config.security.filter.TimestampHashAuthenticationToken;

/**
 * @author Johannes Hiemer.
 *
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Value("${security.token.default}")
	private String token;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String providedToken = authentication.getCredentials().toString();
		
		if (providedToken == null) {
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
		return clazz.equals(TimestampHashAuthenticationToken.class);
	}

}
