package de.haproxyhq.nosql.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Maximilian Büttner, Johannes Hiemer.
 *
 */
@Document
public class HAProxyConfig extends AbstractEntity {

	private List<Section> sections = new ArrayList<Section>();

	public HAProxyConfig() {
		super();
	}

	public HAProxyConfig(List<Section> sections) {
		super();
		this.sections = sections;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

}
