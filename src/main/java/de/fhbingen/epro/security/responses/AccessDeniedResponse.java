package de.fhbingen.epro.security.responses;

public class AccessDeniedResponse {
	private String message;
	private boolean accessDenied;
	private String cause;
	
	public AccessDeniedResponse(String message, boolean accessDenied, String cause) {
		this.setMessage(message);
		this.setAccessDenied(accessDenied);
		this.setCause(cause);
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAccessDenied() {
		return accessDenied;
	}

	public void setAccessDenied(boolean accessDenied) {
		this.accessDenied = accessDenied;
	}
}
