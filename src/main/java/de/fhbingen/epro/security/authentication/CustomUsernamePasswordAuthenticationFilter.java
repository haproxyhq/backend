package de.fhbingen.epro.security.authentication;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.gson.Gson;

import de.fhbingen.epro.security.errors.UsernamePasswordValidationErrors;
import de.fhbingen.epro.web.validation.ValidationEntity;
import de.fhbingen.epro.web.validation.utils.ValidationUtils;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		String username = null,
			   password = null;
		if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
			response.setStatus(HttpStatus.OK.value());
			return null;
		} else if (request.getMethod().equals("POST")) {
			if (verifyHeaderContentType(request) || request.getHeader("Accept") != null)
				response.addHeader("Content-Type", request.getHeader("Accept"));
			
			if (verifyHeaderContentType(request)) {
				//TODO: loginBean
				/*
				try {
					loginBean = requestToLoginBean(request);
				} catch (IOException e) {
					response.setStatus(HttpStatus.BAD_REQUEST.value());
				}*/
			}
			
			username = obtainUsername(request);			
			password = obtainPassword(request);

			UsernamePasswordValidationErrors errors = new UsernamePasswordValidationErrors("user");

			if (username == null || username == "")
				ValidationUtils.rejectBlank(errors, "username", "Field may not be empty");
			if (password == null || password == "")
				ValidationUtils.rejectBlank(errors, "password", "Field may not be empty");
			
			if (errors.hasErrors()) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				try {
					response.getWriter()
						.append(convertObjectToJson(ValidationUtils.resolveResponse("user", errors)))
						.flush();
					return null;
				} catch (IOException e) {
					throw new AuthenticationServiceException("Error generating BAD_REQUEST response", e.getCause());
				}			
			}
			
			username = username.toLowerCase().trim();
			password = password.trim();
			
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					username, password);

			setDetails(request, authRequest);
			Authentication authentication = this.getAuthenticationManager().authenticate(authRequest); 
			
			return authentication;
		} else {
			throw new AuthenticationServiceException(
					"HTTP method not supported: " + request.getMethod());
		}
	}

	private boolean verifyHeaderContentType(HttpServletRequest request) {
		if(request.getContentType().equals("application/json")) return true;
		if(request.getContentType().equals("application/json;charset=utf-8")) return true;
		return false;
	}

	private CharSequence convertObjectToJson(ValidationEntity resolveResponse) {
		Gson gson = new Gson();
		return gson.toJson(resolveResponse);
	}
}
