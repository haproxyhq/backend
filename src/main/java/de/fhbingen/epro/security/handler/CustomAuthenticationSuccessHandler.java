package de.fhbingen.epro.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import de.fhbingen.epro.security.token.TokenUtil;
import de.fhbingen.epro.sql.User;

@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private TokenUtil tokenUtil;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		
		//TODO: send redirect with token
		tokenUtil.addHeader(response, ((User) auth.getPrincipal()).getEmail());
		//response.addHeader("location", "");
		response.sendRedirect("");
	}

}
