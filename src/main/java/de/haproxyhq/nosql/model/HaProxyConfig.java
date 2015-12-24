package de.haproxyhq.nosql.model;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author Maximilian Büttner
 *
 */
public class HaProxyConfig extends AbstractEntity {

	private HashMap<String, List<String>> global;
	private HashMap<String, List<String>> defaults;
	private HashMap<String, List<String>> listen;
	private HashMap<String, List<String>> frontend;
	private HashMap<String, List<String>> backend;

	public HaProxyConfig() {
		super();
	}

	public HaProxyConfig(HashMap<String, List<String>> global, HashMap<String, List<String>> defaults,
			HashMap<String, List<String>> listen, HashMap<String, List<String>> frontend,
			HashMap<String, List<String>> backend) {
		super();
		this.global = global;
		this.defaults = defaults;
		this.listen = listen;
		this.frontend = frontend;
		this.backend = backend;
	}

	public HashMap<String, List<String>> getGlobal() {
		return global;
	}

	public void setGlobal(HashMap<String, List<String>> global) {
		this.global = global;
	}

	public HashMap<String, List<String>> getDefaults() {
		return defaults;
	}

	public void setDefaults(HashMap<String, List<String>> defaults) {
		this.defaults = defaults;
	}

	public HashMap<String, List<String>> getListen() {
		return listen;
	}

	public void setListen(HashMap<String, List<String>> listen) {
		this.listen = listen;
	}

	public HashMap<String, List<String>> getFrontend() {
		return frontend;
	}

	public void setFrontend(HashMap<String, List<String>> frontend) {
		this.frontend = frontend;
	}

	public HashMap<String, List<String>> getBackend() {
		return backend;
	}

	public void setBackend(HashMap<String, List<String>> backend) {
		this.backend = backend;
	}
}
