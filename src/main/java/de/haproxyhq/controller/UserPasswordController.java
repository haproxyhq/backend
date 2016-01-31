package de.haproxyhq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.haproxyhq.sql.model.User;
import de.haproxyhq.sql.repositories.UserRepository;

@Controller
public class UserPasswordController {

		@Autowired
		UserRepository userRepository;
		
		@Autowired
		PasswordEncoder passwordEncoder;

		@RequestMapping(value = "/users/{id}/password", method = RequestMethod.PATCH)
		public ResponseEntity<User> changePassword(@PathVariable Long id, @RequestBody User user) {
			User origUser = userRepository.findOne(id);

			if (user.getPassword() != null) {
				origUser.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			
			User respUser = userRepository.save(origUser);
			return new ResponseEntity<User>(respUser, HttpStatus.OK);
		}

}
