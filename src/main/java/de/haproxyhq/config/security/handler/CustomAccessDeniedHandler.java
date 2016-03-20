/**
 * 
 */
package de.haproxyhq.config.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 
 * @author Johannes Hiemer.
 *
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
       
	@Override
        public void handle(HttpServletRequest request, HttpServletResponse response, 
        		AccessDeniedException accessDeniedException) throws IOException, ServletException {
 
			response.setHeader("Content-Type", "application/json; charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter out = response.getWriter();
            out.print("{ \"message\" : \"You are not privileged to request this resource.\", "
            		+ "\"access-denied\" : true,\"cause\" : \"AUTHORIZATION_FAILURE\" }");
            out.flush();
            out.close();
 
        }
}