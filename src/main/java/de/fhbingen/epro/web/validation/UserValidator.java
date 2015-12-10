package de.fhbingen.epro.web.validation;


import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.fhbingen.epro.sql.User;
import de.fhbingen.epro.sql.repositories.UserRepository;
import de.fhbingen.epro.web.validation.utils.ValidationUtils;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserRepository userRepository;

	private PasswordEncoder passwortEncoder = new BCryptPasswordEncoder();

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectBlank(errors, "firstName", "field.required");
		ValidationUtils.rejectBlank(errors, "name", "field.required");
		ValidationUtils.rejectBlank(errors, "email", "field.required");
		ValidationUtils.rejectBlank(errors, "password", "field.required");
		User user = (User) target;
		if (user.getPassword().length() < 7)
			errors.rejectValue("password", "field.too.short");
		if (!user.getPassword().matches("[A-Za-z0-9]+"))
			errors.rejectValue("password", "field.not.alphanumeric");
		if (!EmailValidator.getInstance(true).isValid(user.getEmail()))
			errors.rejectValue("email", "email.invalid");

		if (userRepository.findUserByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "email.taken");
		}

		// TODO: besser in event-handler
		user.setPassword(passwortEncoder.encode(user.getPassword()));
	}

}
