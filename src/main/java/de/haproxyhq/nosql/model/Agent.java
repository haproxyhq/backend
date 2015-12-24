package de.haproxyhq.nosql.model;

/**
 * 
 * @author Maximilian Büttner
 *
 */
public class Agent extends AbstractEntity {
	
	private String name;
	private HaProxyConfig config;
	
	public Agent() {
		super();
	}
	
	public Agent(String name, HaProxyConfig config) {
		super();
		this.name = name;
		this.config = config;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HaProxyConfig getConfig() {
		return config;
	}

	public void setConfig(HaProxyConfig config) {
		this.config = config;
	}
	
	
}
