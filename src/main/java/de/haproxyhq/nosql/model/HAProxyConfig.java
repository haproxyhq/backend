package de.haproxyhq.nosql.model;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author Maximilian Büttner
 *
 */
public class HAProxyConfig extends AbstractEntity {
	
	List<Section> config;
	
	public HAProxyConfig() {
		super();
	}

	public HAProxyConfig(List<Section> config) {
		super();
		this.config = config;
	}

	public static class Section {
		HashMap<String, String> section;
		List<String> values;

		public Section() {
			super();
		}

		public Section(HashMap<String, String> section, List<String> values) {
			super();
			this.section = section;
			this.values = values;
		}

		public HashMap<String, String> getSection() {
			return section;
		}
		public void setSection(HashMap<String, String> section) {
			this.section = section;
		}
		public List<String> getValues() {
			return values;
		}
		public void setValues(List<String> values) {
			this.values = values;
		}
	}

	public List<Section> getConfig() {
		return config;
	}

	public void setConfig(List<Section> config) {
		this.config = config;
	}

}
