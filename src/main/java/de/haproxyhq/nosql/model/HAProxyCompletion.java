package de.haproxyhq.nosql.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author Maximilian Büttner.
 *
 */
@Document
public class HaProxyCompletion extends AbstractEntity {
	
	private String url;

	private String version;

	private List<Section> data;

	public static class Section {
		private String keyword;
		private String params;
		private String anchor;

		public Section() {
			super();
		}

		public Section(String keyword, String params, String anchor) {
			super();
			this.keyword = keyword;
			this.params = params;
			this.anchor = anchor;
		}

		public String getKeyword() {
			return keyword;
		}

		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}

		public String getParams() {
			return params;
		}

		public void setParams(String params) {
			this.params = params;
		}

		public String getAnchor() {
			return anchor;
		}

		public void setAnchor(String anchor) {
			this.anchor = anchor;
		}

	}

	public HaProxyCompletion() {
		super();
	}

	public HaProxyCompletion(String url, String version, List<Section> data) {
		super();
		this.url = url;
		this.version = version;
		this.data = data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<Section> getData() {
		return data;
	}

	public void setData(List<Section> data) {
		this.data = data;
	}

}
