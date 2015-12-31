package de.haproxyhq.nosql.model;

/**
 * 
 * @author Maximilian Büttner
 *
 */
public class Agent extends AbstractEntity {
	
	private String name;
	private HAProxyConfig configHolder;
	
	public Agent() {
		super();
	}
	
	public Agent(String name, HAProxyConfig config) {
		super();
		this.name = name;
		this.configHolder = config;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HAProxyConfig getConfig() {
		return configHolder;
	}

	public void setConfig(HAProxyConfig config) {
		this.configHolder = config;
	}
	
	
}
