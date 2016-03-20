/**
 * 
 */
package de.haproxyhq.config.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 
 * @author Johannes Hiemer.
 *
 */
public class FixedTokenProcessingFilter extends GenericFilterBean {

    private AuthenticationManager authManager;

    public FixedTokenProcessingFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest req = (HttpServletRequest) request;
    	
    	if (!req.getMethod().equals(HttpMethod.OPTIONS.toString())) {
	        if(req.getHeader("token") != null) {
	            String token = req.getHeader("token");
	
	            if (token != null) {
	
	                TimestampHashAuthenticationToken authentication = 
	                        new TimestampHashAuthenticationToken(token);
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
	
	                SecurityContextHolder.getContext().setAuthentication(authManager.authenticate(authentication));         
	            }
	        }
	        chain.doFilter(request, response);
	    }
    }
}
