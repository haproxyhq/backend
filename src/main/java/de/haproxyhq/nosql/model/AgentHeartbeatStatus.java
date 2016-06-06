package de.haproxyhq.nosql.model;

/**
 * 
 * @author Jonas Depoix
 */
public class AgentHeartbeatStatus {

	private boolean agentAlive;

	private boolean haProxyAlive;
	
	public AgentHeartbeatStatus(boolean agentAlive, boolean haProxyAlive) {
		this.agentAlive = agentAlive;
		this.haProxyAlive = haProxyAlive;
	}
	
	public boolean isAgentAlive() {
		return agentAlive;
	}
	
	public void setAgentAlive(boolean agentAlive) {
		this.agentAlive = agentAlive;
	}
	
	public boolean isHAProxyAlive() {
		return haProxyAlive;
	}
	
	public void setHAProxyAlive(boolean haProxyAlive) {
		this.haProxyAlive = haProxyAlive;
	}
}
