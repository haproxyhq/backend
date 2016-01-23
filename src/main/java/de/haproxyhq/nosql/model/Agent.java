package de.haproxyhq.nosql.model;

/**
 * 
 * @author Maximilian Büttner
 *
 */
public class Agent extends AbstractEntity {

	private String name;
	private String description;
	private String ip;
	private String version;
	private HAProxyConfig configHolder;
	private long configTimestamp;

	public Agent() {
		super();
	}

	public Agent(String name, HAProxyConfig configHolder) {
		super();
		this.name = name;
		this.configHolder = configHolder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HAProxyConfig getConfigHolder() {
		return configHolder;
	}

	public void setConfigHolder(HAProxyConfig configHolder) {
		this.configHolder = configHolder;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getConfigTimestamp() {
		return configTimestamp;
	}

	public void setConfigTimestamp(long configTimestamp) {
		this.configTimestamp = configTimestamp;
	}
}
