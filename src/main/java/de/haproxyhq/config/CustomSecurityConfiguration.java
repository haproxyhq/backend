/**
 * 
 */
package de.haproxyhq.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import de.haproxyhq.config.security.CustomAuthenticationProvider;
import de.haproxyhq.config.security.filter.FixedTokenProcessingFilter;
import de.haproxyhq.config.security.handler.CustomAccessDeniedHandler;
import de.haproxyhq.config.security.handler.CustomAuthenticationEntryPoint;
import de.haproxyhq.config.security.handler.CustomAuthenticationFailureHandler;
import de.haproxyhq.config.security.handler.CustomAuthenticationSuccessHandler;

/**
 * 
 * @author Johannes Hiemer.
 *
 */
@Configuration
@EnableWebSecurity
public class CustomSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
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

		http.addFilterBefore(fixedTokenProcessingFilter(), LogoutFilter.class)
		.csrf().disable()

		.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

		.and()

		.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler())
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint())			

		.and()

		.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/**").authenticated()
			.antMatchers(HttpMethod.POST, "/**").authenticated()
			.antMatchers(HttpMethod.DELETE, "/**").authenticated();
	}
	
	@Bean 
	public CustomAuthenticationProvider customAuthenticationProvider() {
		return new de.haproxyhq.config.security.CustomAuthenticationProvider();
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
	
	@Bean
	public Filter fixedTokenProcessingFilter() throws Exception {
		return new FixedTokenProcessingFilter(authenticationManagerBean());
	}
	
}
