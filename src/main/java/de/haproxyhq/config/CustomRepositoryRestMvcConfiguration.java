package de.haproxyhq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.mvc.ControllerLinkBuilderFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import de.haproxyhq.sql.eventhandler.UserEventHandler;
import de.haproxyhq.web.validation.AgentUpdateValidator;
import de.haproxyhq.web.validation.UserUpdateValidator;
import de.haproxyhq.web.validation.UserValidator;

/**
 * 
 * @author Johannes Hiemer, Maximilian Büttner
 *
 */
@Configuration
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@Import(value = { CustomSecurityConfiguration.class })
public class CustomRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserUpdateValidator userUpdateValidator;
	
	@Autowired
	private AgentUpdateValidator agentUpdateValidator;
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(CustomRepositoryRestMvcConfiguration.class);

	@Bean(autowire = Autowire.BY_TYPE)
	public ControllerLinkBuilderFactory controllerLinkBuilderFacotry() {
		return new ControllerLinkBuilderFactory();
	}

	/**
	 * SD REST Settings
	 */
	@Override
	protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.setReturnBodyOnCreate(true);
		config.setReturnBodyOnUpdate(true);
		config.setMaxPageSize(100);
		config.setDefaultPageSize(25);
		config.setDefaultMediaType(MediaType.APPLICATION_JSON);
		config.useHalAsDefaultJsonMediaType(false);
	}

	@Override
	protected void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
		v.addValidator("beforeCreate", userValidator);
		v.addValidator("beforeSave", userUpdateValidator);
		v.addValidator("beforeSave", agentUpdateValidator);
	}

	@Bean
	UserEventHandler userEventHandler() {
		return new UserEventHandler();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
		resource.setBasename("classpath:messages");
		resource.setDefaultEncoding("UTF-8");
		return resource;
	}

}
