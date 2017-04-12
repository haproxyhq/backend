
package de.haproxyhq.controller.schema.types;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rene Schollmeyer, Sebastian Boeing, evoila.
 *
 *This cLass represents the necessary parameters for HAProxy to make a service available  
 */
public class InternalConnectionDetails {

	private String ip;
	
	private int port;
	
	private String mode;
	
	List<String> options;
	
	public InternalConnectionDetails() {
	}

	public InternalConnectionDetails(String ip, int port, String mode, List<String> options) {
		super();
		this.ip = ip;
		this.port = port;
		this.mode = mode;
		setOptions(options);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		if(options == null)
			this.options = new ArrayList<String>();
		else
			this.options = options;
	}
	
	public String getIdentifier() {
		return this.ip.concat("-").concat(Integer.toString(this.port));
	}
	
}