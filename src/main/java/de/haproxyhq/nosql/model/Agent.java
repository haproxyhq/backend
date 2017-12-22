package de.haproxyhq.nosql.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author Maximilian Büttner, Jonas Depoix, Johannes Hiemer.
 */
@Document
public class Agent extends AbstractEntity {

	private String name;

	private String description;

	private String ip;

	private String version;

	private String authToken;

	private HAProxyConfig haProxyConfig;

	private long configTimestamp;

	private long agentHeartbeatTimestamp;

	private long haProxyHeartbeatTimestamp;

	public Agent() {
		super();
	}

	public Agent(String name, HAProxyConfig haProxyConfig) {
		super();
		this.name = name;
		this.haProxyConfig = haProxyConfig;
	}

	public AgentHeartbeatStatus getAgentHeartbeat() {
		long currentTimestamp = System.currentTimeMillis();

		return new AgentHeartbeatStatus(Math.abs(this.getAgentHeartbeatTimestamp() - currentTimestamp) < 120000,
				Math.abs(this.getHaProxyHeartbeatTimestamp() - currentTimestamp) < 120000);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HAProxyConfig getHaProxyConfig() {
		return haProxyConfig;
	}

	public void setHaProxyConfig(HAProxyConfig haProxyConfig) {
		this.haProxyConfig = haProxyConfig;
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

	public long getHaProxyHeartbeatTimestamp() {
		return haProxyHeartbeatTimestamp;
	}

	public void setHaProxyHeartbeatTimestamp(long haProxyHeartbeatTimestamp) {
		this.haProxyHeartbeatTimestamp = haProxyHeartbeatTimestamp;
	}

	public long getAgentHeartbeatTimestamp() {
		return agentHeartbeatTimestamp;
	}

	public void setAgentHeartbeatTimestamp(long agentHeartbeatTimestamp) {
		this.agentHeartbeatTimestamp = agentHeartbeatTimestamp;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

}
