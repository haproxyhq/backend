package de.haproxyhq.web.validation;


import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import de.haproxyhq.sql.model.User;
import de.haproxyhq.sql.repositories.UserRepository;
import de.haproxyhq.web.validation.utils.ValidationUtils;

/**
 * 
 * @author Maximilian Büttner
 *
 */
@Component
public class UserUpdateValidator implements Validator {

	//@Autowired()
	//private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//User user = (User) target;
		//TODO: do magic with reflection to check if fields are set and then not empty
		ValidationUtils.rejectBlank(errors, "firstName", "field.required");
		ValidationUtils.rejectBlank(errors, "name", "field.required");
		ValidationUtils.rejectBlank(errors, "email", "field.required");

		/*ValidationUtils.rejectBlank(errors, "password", "field.required");
		User user = (User) target;
		if (user.getPassword().length() < 7)
			errors.rejectValue("password", "field.too.short");
		if (!user.getPassword().matches("[A-Za-z0-9]+"))
			errors.rejectValue("password", "field.not.alphanumeric");
		if (!EmailValidator.getInstance(true).isValid(user.getEmail()))
			errors.rejectValue("email", "email.invalid");

		//find the email -> if found, check if found.id=this.id
		if (userRepository.findUserByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "email.taken");
		}*/
	}
}
