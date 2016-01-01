package de.haproxyhq.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.haproxyhq.nosql.model.HAProxyConfig;

public class TestUtils {

	public static HAProxyConfig createExampleHaProxyConfig(){

		HAProxyConfig configHolder = new HAProxyConfig();
		
		List<HAProxyConfig.Section> config = new ArrayList<>();
		HAProxyConfig.Section globals = new HAProxyConfig.Section();
		HAProxyConfig.Section backend = new HAProxyConfig.Section();
		HAProxyConfig.Section listen = new HAProxyConfig.Section();
		
		HashMap<String, String> sectionGlobals = new HashMap<>();
		sectionGlobals.put("type", "globals");
		sectionGlobals.put("name", "");
		
		List<String> valuesGlobals = new ArrayList<>();
		valuesGlobals.add("daemon");
		valuesGlobals.add("maxconn 256");

		globals.setSection(sectionGlobals);
		globals.setValues(valuesGlobals);
		
		HashMap<String, String> sectionBackend = new HashMap<>();
		sectionBackend.put("type", "backend");
		sectionBackend.put("name", "mybackend");
		
		List<String> valuesBackend = new ArrayList<>();
		valuesBackend.add("server1 127.0.0.1:8000 maxconn 32");
		
		backend.setSection(sectionBackend);
		backend.setValues(valuesBackend);
		
		HashMap<String, String> sectionListen = new HashMap<>();
		sectionListen.put("type", "listen");
		sectionListen.put("name", "http-in");
		
		List<String> valuesListen = new ArrayList<>();
		valuesListen.add("server server1 127.0.0.1:8000 maxconn 32");
		
		listen.setSection(sectionListen);
		listen.setValues(valuesListen);
		
		config.add(globals);
		config.add(backend);
		config.add(listen);
		
		configHolder.setConfig(config);
		
		return configHolder;
	}
}
