/**
 * 
 */
package de.haproxyhq.config.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 
 * @author Johannes Hiemer.
 *
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, 
        		AuthenticationException authException) throws IOException, ServletException {
 
        	response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter out = response.getWriter();
            out.print("{ \"message\" : \"Full authentication is required to access this resource.\","
            		+ " \"access-denied\" : true,\"cause\" : \"NOT AUTHENTICATED\"}");
            out.flush();
            out.close();
        }
    }