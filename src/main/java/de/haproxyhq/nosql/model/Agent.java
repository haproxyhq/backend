package de.haproxyhq.nosql.model;

import de.haproxyhq.nosql.model.AgentHeartbeatStatus;

/**
 * 
 * @author Maximilian Büttner, jdepoix
 */
public class Agent extends AbstractEntity {

	private String name;
	private String description;
	private String ip;
	private String version;
	private HAProxyConfig configHolder;
	private long configTimestamp;
	private long agentHeartbeatTimestamp;
	private long haproxyHeartbeatTimestamp;

	public Agent() {
		super();
	}

	public Agent(String name, HAProxyConfig configHolder) {
		super();
		this.name = name;
		this.configHolder = configHolder;
	}
	
	public AgentHeartbeatStatus getAgentHeartbeat() {
		long currentTimestamp = System.currentTimeMillis();

		return new AgentHeartbeatStatus(
			Math.abs(this.getAgentHeartbeatTimestamp() - currentTimestamp) < 120000,
			Math.abs(this.getHaproxyHeartbeatTimestamp() - currentTimestamp) < 120000
		);
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

	public long getHaproxyHeartbeatTimestamp() {
		return haproxyHeartbeatTimestamp;
	}

	public void setHaproxyHeartbeatTimestamp(long haproxyHeartbeatTimestamp) {
		this.haproxyHeartbeatTimestamp = haproxyHeartbeatTimestamp;
	}

	public long getAgentHeartbeatTimestamp() {
		return agentHeartbeatTimestamp;
	}

	public void setAgentHeartbeatTimestamp(long agentHeartbeatTimestamp) {
		this.agentHeartbeatTimestamp = agentHeartbeatTimestamp;
	}
}
