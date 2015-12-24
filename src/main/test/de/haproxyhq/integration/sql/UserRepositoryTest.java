package de.haproxyhq.integration.sql;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.haproxyhq.BaseContextConfiguration;
import de.haproxyhq.sql.User;
import de.haproxyhq.sql.repositories.UserRepository;

public class UserRepositoryTest extends BaseContextConfiguration {

	private static final String FIRST_NAME = "Max";
	private static final String NAME = "Mustermann";
	private static final String EMAIL = "integration@test.de";
	private static final String PASSWORD = "123456";
	
	@Autowired
	UserRepository userRepo;
	
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	User testUser;
	
	public UserRepositoryTest() {
		super();
	}
	
	@Before
	public void createTestData() {
		User user = new User(FIRST_NAME, NAME, EMAIL, passwordEncoder.encode(PASSWORD));
		testUser = userRepo.save(user);
	}

	@Test
	public void findUserByEmailTest() {
		User user = userRepo.findUserByEmail(EMAIL);
		assertNotNull(user);
		assertEquals(testUser.getId(), user.getId());
	}
	
	@After
	public void deleteTestData() {
		userRepo.delete(testUser);
	}
	
}
