package de.haproxyhq.nosql.model;

/**
 * 
 * @author Jonas Depoix.
 */
public class Schema extends AbstractEntity {
	
	private String name;
	private String description;
	private String version;

	private HaProxyConfig configHolder;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HaProxyConfig getConfigHolder() {
		return configHolder;
	}

	public void setConfigHolder(HaProxyConfig configHolder) {
		this.configHolder = configHolder;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
