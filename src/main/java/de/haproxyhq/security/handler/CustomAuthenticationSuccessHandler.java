package de.haproxyhq.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import de.haproxyhq.security.responses.AuthenticationSuccessResponse;
import de.haproxyhq.security.token.TokenUtil;
import de.haproxyhq.sql.model.User;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		String token;
		try {
			token = tokenUtil.createAuthToken(((User) auth.getPrincipal()).getEmail());

			String json = gson.toJson(new AuthenticationSuccessResponse(token));

			PrintWriter out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

	
}
