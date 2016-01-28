package de.haproxyhq.nosql.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.haproxyhq.nosql.model.AgentHeartbeatStatus;
import de.haproxyhq.utils.AgentUtils;

/**
 * 
 * @author Maximilian Büttner, jdepoix
 */
public class Agent extends AbstractEntity implements UserDetails {

	private static final long serialVersionUID = 7562366999557112617L;

	private String name;
	private String description;
	private String ip;
	private String version;
	private String authToken;
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

		return new AgentHeartbeatStatus(Math.abs(this.getAgentHeartbeatTimestamp() - currentTimestamp) < 120000,
				Math.abs(this.getHaproxyHeartbeatTimestamp() - currentTimestamp) < 120000);
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
	
	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	@Transient
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new GrantedAuthority() {

			private static final long serialVersionUID = -5440166193605354062L;

			@Override
			public String getAuthority() {
				return AgentUtils.agentGroup;
			}
		});
		return authorities;
	}

	@Transient
	@JsonIgnore
	@Override
	public String getPassword() {
		return "";
	}

	@Transient
	@JsonIgnore
	@Override
	public String getUsername() {
		return AgentUtils.agentPrefix + getId();
	}

	@Transient
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

}
