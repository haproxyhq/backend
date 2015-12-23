package de.haproxyhq.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.google.gson.Gson;

import de.haproxyhq.security.responses.AccessDeniedResponse;

/**
 * 
 * @author Maximilian Büttner
 *
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	@Autowired
	private Gson gson;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);

		String json = gson.toJson(new AccessDeniedResponse("You are not privileged to request this resource", true, "AUTHORIZATION_FAILURE"));

		PrintWriter out = response.getWriter();
		out.println(json);
		out.flush();
		out.close();
	}

}
