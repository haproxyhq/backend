package de.haproxyhq.nosql.model;

/**
 * this is a model to hold information about the heartbeat of a agent entity
 * 
 * @author jdepoix
 */
public class AgentHeartbeatStatus {
	/**
	 * whether the agent is alive, or not
	 */
	private boolean agentAlive;
	/**
	 * whether the haproxy monitored by this agent is alive, or not
	 */
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
