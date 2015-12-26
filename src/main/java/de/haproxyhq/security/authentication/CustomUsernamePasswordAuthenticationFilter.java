package de.haproxyhq.security.authentication;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.JSONString;
import org.json.JSONStringer;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.gson.Gson;

import de.haproxyhq.security.errors.UsernamePasswordValidationErrors;
import de.haproxyhq.web.validation.ValidationEntity;
import de.haproxyhq.web.validation.utils.ValidationUtils;

/**
 * 
 * @author Johannes Hiemer, Maximilian Büttner
 *
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		String username = null, password = null;
		if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
			response.setStatus(HttpStatus.OK.value());
			return null;
		} else if (request.getMethod().equals("POST")) {
			if (verifyHeaderContentType(request) || request.getHeader("Accept") != null)
				response.addHeader("Content-Type", request.getHeader("Accept"));

			Set<Entry<String, String[]>> entries = request.getParameterMap().entrySet();
			if(entries.size() == 1){
				for (Entry<String, String[]> entry : entries) {
					JSONObject loginObject = new JSONObject(entry.getKey());
					username = loginObject.getString("username");
					password = loginObject.getString("password");
				}
			}

			UsernamePasswordValidationErrors errors = new UsernamePasswordValidationErrors("user");

			if (username == null || username == "")
				ValidationUtils.rejectBlank(errors, "username", "Field may not be empty");
			if (password == null || password == "")
				ValidationUtils.rejectBlank(errors, "password", "Field may not be empty");

			if (errors.hasErrors()) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				try {
					response.getWriter().append(convertObjectToJson(ValidationUtils.resolveResponse("user", errors)))
							.flush();
					return null;
				} catch (IOException e) {
					throw new AuthenticationServiceException("Error generating BAD_REQUEST response", e.getCause());
				}
			}

			username = username.toLowerCase().trim();
			password = password.trim();

			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
					password);

			setDetails(request, authRequest);
			Authentication authentication = this.getAuthenticationManager().authenticate(authRequest);

			return authentication;
		} else {
			throw new AuthenticationServiceException("HTTP method not supported: " + request.getMethod());
		}
	}

	private boolean verifyHeaderContentType(HttpServletRequest request) {
		if (request.getContentType().equals("application/json"))
			return true;
		if (request.getContentType().equals("application/json;charset=utf-8"))
			return true;
		return false;
	}

	private CharSequence convertObjectToJson(ValidationEntity resolveResponse) {
		Gson gson = new Gson();
		return gson.toJson(resolveResponse);
	}
}
