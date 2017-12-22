/**
 * 
 */
package de.haproxyhq.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * @author Johannes Hiemer.
 *
 */
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
		String accessDenied = "{ \"failure\" : \"Access Denied\" }";
		
		response.setHeader("Content-Type", "application/json; charset=UTF-8");
		response.getWriter().append(accessDenied);
				
		if(exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentLength(accessDenied.length());
        }
		response.flushBuffer();
		
	}
}