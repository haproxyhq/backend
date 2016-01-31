package de.haproxyhq.sql.eventhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.haproxyhq.sql.model.User;

@RepositoryEventHandler(User.class)
public class UserEventHandler {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@HandleBeforeCreate
	public void handleBeforeCreate(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	}

}
