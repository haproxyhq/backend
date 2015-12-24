package de.haproxyhq.integration.nosql;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;

import de.haproxyhq.BaseContextConfiguration;
import de.haproxyhq.nosql.model.HaProxyConfig;
import de.haproxyhq.nosql.repositories.HaProxyConfigRepository;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@IntegrationTest
public class HaProxyConfigRepositoryTest extends BaseContextConfiguration {

	@Autowired
	HaProxyConfigRepository configRepo;

	HaProxyConfig testHapc;
	
	public HaProxyConfigRepositoryTest() {
		super();
	}
	
	@Before
	public void createTestData() {
		HashMap<String, List<String>> global = new HashMap<>();
		global.put("deamon", new ArrayList<String>());
		global.put("maxconn", Arrays.asList("256"));
		
		HashMap<String, List<String>> defaults = new HashMap<>();
		defaults.put("mode", Arrays.asList("http"));
		defaults.put("timeout", Arrays.asList("connect", "5000ms"));
		
		HashMap<String, List<String>> listen = new HashMap<>();
		listen.put("_header", Arrays.asList("http-in"));
		listen.put("server", Arrays.asList("server1", "127.0.0.1:8000", "maxconn", "32"));
		
		HashMap<String, List<String>> frontend = new HashMap<>();
		frontend.put("_header", Arrays.asList("http-in"));
		frontend.put("bind", Arrays.asList("*:80"));
		
		HashMap<String, List<String>> backend = new HashMap<>();
		backend.put("_header", Arrays.asList("servers"));
		backend.put("server", Arrays.asList("server1", "127.0.0.1:8000", "maxconn", "32"));

		HaProxyConfig hapc = new HaProxyConfig(global, defaults, listen, frontend, backend);
		testHapc = configRepo.save(hapc);
	}
	
	@Test
	public void dummyTest() {
		assertTrue(true);
	}
	
	@After
	public void deleteTestData() {
		configRepo.delete(testHapc);
	}

}
