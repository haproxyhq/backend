package de.fhbingen.epro.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import de.fhbingen.epro.web.validation.UserValidator;

/**
 * 
 * @author Johannes Hiemer.
 *
 */
@Configuration
@EnableHypermediaSupport(type = { HypermediaType.HAL })
@Import(value = { CustomMvcConfiguration.class, CustomSecurityConfiguration.class })
@ComponentScan(basePackages = { "de.fhbingen.epro.web" })
public class CustomRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {

	@Autowired
	private UserValidator userValidator;
	
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
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");
	}

	@Bean
	public ViewResolver configureViewResolver() {
		InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
		viewResolve.setPrefix("/WEB-INF/jsp/views/");
		viewResolve.setSuffix(".jsp");

		return viewResolve;
	}
	
    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
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
