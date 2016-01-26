package de.haproxyhq.nosql.model;

/**
 * entity for config schemas holding the config and metadata
 * 
 * @author jdepoix
 */
public class Schema extends AbstractEntity {
	private String name;
	private String description;
	private String version;

	private HAProxyConfig configHolder;

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

	public HAProxyConfig getConfigHolder() {
		return configHolder;
	}

	public void setConfigHolder(HAProxyConfig configHolder) {
		this.configHolder = configHolder;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
