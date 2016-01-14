/**
 * 
 */
package de.haproxyhq.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import de.haproxyhq.utils.PackageUtils;

import de.haproxyhq.security.CustomUserDetailsService;
import de.haproxyhq.security.authentication.CustomAuthenticationProvider;
import de.haproxyhq.security.authentication.CustomUsernamePasswordAuthenticationFilter;
import de.haproxyhq.security.filter.TokenAuthenticationFilter;
import de.haproxyhq.security.handler.CustomAccessDeniedHandler;
import de.haproxyhq.security.handler.CustomAuthenticationEntryPoint;
import de.haproxyhq.security.handler.CustomAuthenticationFailureHandler;
import de.haproxyhq.security.handler.CustomAuthenticationSuccessHandler;
import de.haproxyhq.security.token.TokenUtil;

/**
 * 
 * @author Johannes Hiemer, Maximilian Büttner
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Bean
	public static PropertyPlaceholderConfigurer securityPropertyPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
		propertyPlaceholderConfigurer.setLocation(new ClassPathResource("application-security.properties"));
		propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		return propertyPlaceholderConfigurer;
	}
	
	@Autowired
    private TokenUtil tokenUtil;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
 	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        List<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
        authenticationProviderList.add(customAuthenticationProvider());
        AuthenticationManager authenticationManager = new ProviderManager(
                authenticationProviderList);
        return authenticationManager;
    }
 
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.addFilterBefore(authenticationFilter(), LogoutFilter.class)
			.addFilter(loginFilter())
		.csrf().disable()

		.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

		.and()

		.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler())
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint())			

		.and()

		.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/j_spring_security_check").permitAll()
			.antMatchers(HttpMethod.POST, "/users").permitAll()
			.antMatchers(HttpMethod.PUT, "/users/reset-password").permitAll()
			.antMatchers(HttpMethod.PUT, "/users/*/password").permitAll()
			.antMatchers(HttpMethod.PUT, "/users/*/email").permitAll()
			.antMatchers(HttpMethod.GET, "/static/**").permitAll()
		
			.antMatchers(HttpMethod.GET, "/").permitAll()
			.antMatchers(HttpMethod.GET, "/**").authenticated()
			.antMatchers(HttpMethod.POST, "/**").authenticated()
			.antMatchers(HttpMethod.PUT, "/**").authenticated()
			.antMatchers(HttpMethod.PATCH, "/**").authenticated()
			.antMatchers(HttpMethod.DELETE, "/**").authenticated()
			.antMatchers(HttpMethod.GET, "/users/isAuthenticated").authenticated();
	}
	
	private Filter authenticationFilter() {
        TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter();
        tokenAuthenticationFilter.setUserDetailsService(customUserDetailsService);
        tokenAuthenticationFilter.setTokenUtil(tokenUtil);
        return tokenAuthenticationFilter;
    }
	
	@Bean
	public Filter loginFilter() throws Exception {
		CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter 
			= new CustomUsernamePasswordAuthenticationFilter();
		customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler());
		customUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler());
		
		return customUsernamePasswordAuthenticationFilter;		
	}
	
	@Bean 
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new CustomAuthenticationProvider();
	}
	
	@Bean
	public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}
	
	@Bean
	public CustomAccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	public CustomAuthenticationFailureHandler customAuthenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}
}
