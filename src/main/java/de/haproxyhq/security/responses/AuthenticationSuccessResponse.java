package de.haproxyhq.security.responses;

public class AuthenticationSuccessResponse {
	private String accessToken;

	public AuthenticationSuccessResponse(String accessToken) {
		super();
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}	
}
