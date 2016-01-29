package de.haproxyhq.sql.eventhandler;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.haproxyhq.sql.model.User;

@RepositoryEventHandler(User.class)
public class UserEventHandler {
	
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@HandleBeforeCreate
	public void handleBeforeCreate(User user) {
		if(user.getPassword() == null) {
			user.setPassword(KeyGenerators.secureRandom().generateKey().toString());
		}
		
		this.sendUserMail(user);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));			
	}
	
	private void sendUserMail(User user) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		try {
			mimeMessageHelper.setSubject(messageSource.getMessage("passwordemail.subject", null, Locale.getDefault()));
			mimeMessageHelper.setTo("jdepoix@seibert-media.net");
			mimeMessageHelper.setText(String.format(
					messageSource.getMessage("passwordemail.text", null, Locale.getDefault()),
					user.getFirstName(),
					user.getName(),
					user.getUsername(),
					user.getPassword()
			));
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		javaMailSender.send(mimeMessage);
	}
}
