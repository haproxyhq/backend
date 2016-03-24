/**
 * 
 */
package de.haproxyhq.utils;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Johannes Hiemer.
 *
 */
public class ResponseMessage {
	
	private long timestamp;

	@JsonProperty("message")
	private String message;

	public ResponseMessage(String message) {
		this.timestamp = Instant.now().toEpochMilli();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
}
