package de.haproxyhq.nosql.model;

/**
 *
 * @author Jonas Depoix.
 */
public class HAProxyConfigSchema extends AbstractEntity {

	private String name;

	private String description;

	private String version;

	private HaProxyConfig haProxyConfig;

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

	public HaProxyConfig getHAProxyConfig() {
		return haProxyConfig;
	}

	public void setHAProxyConfig(HaProxyConfig haProxyConfig) {
		this.haProxyConfig = haProxyConfig;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
