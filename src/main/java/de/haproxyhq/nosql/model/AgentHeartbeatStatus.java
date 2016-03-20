package de.haproxyhq.nosql.model;

/**
 * 
 * @author Jonas Depoix
 */
public class AgentHeartbeatStatus {

	private boolean agentAlive;

	private boolean haproxyAlive;
	
	public AgentHeartbeatStatus(boolean agentAlive, boolean haproxyAlive) {
		this.agentAlive = agentAlive;
		this.haproxyAlive = haproxyAlive;
	}
	
	public boolean isAgentAlive() {
		return agentAlive;
	}
	
	public void setAgentAlive(boolean agentAlive) {
		this.agentAlive = agentAlive;
	}
	
	public boolean isHaproxyAlive() {
		return haproxyAlive;
	}
	
	public void setHaproxyAlive(boolean haproxyAlive) {
		this.haproxyAlive = haproxyAlive;
	}
}
