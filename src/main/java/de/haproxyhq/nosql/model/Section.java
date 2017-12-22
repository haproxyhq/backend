/**
 * 
 */
package de.haproxyhq.nosql.model;

import java.util.List;
import java.util.Map;

public class Section {
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