package de.haproxyhq.integration.nosql;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;

import de.haproxyhq.BaseContextConfiguration;
import de.haproxyhq.nosql.model.HAProxyConfig;
import de.haproxyhq.nosql.repositories.HAProxyConfigRepository;
import de.haproxyhq.utils.TestUtils;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@IntegrationTest
public class HAProxyConfigRepositoryTest extends BaseContextConfiguration {

	@Autowired
	HAProxyConfigRepository configRepo;

	HAProxyConfig testHapc;
	
	public HAProxyConfigRepositoryTest() {
		super();
	}
	
	@Before
	public void createTestData() {
		testHapc = configRepo.save(TestUtils.createExampleHaProxyConfig());
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
