package de.haproxyhq.nosql.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author Maximilian Büttner, Johannes Hiemer.
 *
 */
@Document
public class HaProxyConfig extends AbstractEntity {
	
	private List<Section> sections = new ArrayList<Section>();
	
	public HaProxyConfig() {
		super();
	}

	public HaProxyConfig(List<Section> sections) {
		super();
		this.sections = sections;
	}

	public static class Section {
		Map<String, String> section;
		List<String> values;

		public Section() {
			super();
		}

		public Section(Map<String, String> section, List<String> values) {
			super();
			this.section = section;
			this.values = values;
		}

		public Map<String, String> getSection() {
			return section;
		}
		public void setSection(Map<String, String> section) {
			this.section = section;
		}
		public List<String> getValues() {
			return values;
		}
		public void setValues(List<String> values) {
			this.values = values;
		}
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

}
