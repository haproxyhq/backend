/**
 * 
 */
package de.haproxyhq.controller.schema.types;

/**
 *@author Johannes Hiemer.
 *
 *This class represents the connection details to connect from an external network to a service
 */
public class ConnectionDetails {

	private String ip;
	
	private int port;
	
	public ConnectionDetails() {
	}

	public ConnectionDetails(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
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
	
	public String getIdentifier() {
		return this.ip.concat("-").concat(Integer.toString(this.port));
	}
	
}
