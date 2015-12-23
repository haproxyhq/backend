package de.haproxyhq.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import de.haproxyhq.security.CustomUserDetailsService;
import de.haproxyhq.security.token.TokenUtil;

/**
 * 
 * @author Johannes Hiemer, Maximilian Büttner
 *
 */
public class TokenAuthenticationFilter implements Filter {

	private CustomUserDetailsService userDetailsService;

	private TokenUtil tokenUtil;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		if (!req.getMethod().equals(HttpMethod.OPTIONS.toString())) {
			UserDetails userDetails = loadUserDetails((HttpServletRequest) request);

			SecurityContext contextBeforeChainExecution = createSecurityContext(userDetails);
			try {

				SecurityContextHolder.setContext(contextBeforeChainExecution);

				if (contextBeforeChainExecution.getAuthentication() != null
						&& contextBeforeChainExecution.getAuthentication().isAuthenticated()) {
					String username = (String) contextBeforeChainExecution.getAuthentication().getPrincipal();
					tokenUtil.addHeader((HttpServletResponse) response, username);
				}
				chain.doFilter(request, response);
			}

			finally {
				SecurityContextHolder.clearContext();
			}
		}
	}

	private SecurityContext createSecurityContext(UserDetails userDetails) {
		if (userDetails != null) {
			SecurityContextImpl securityContext = new SecurityContextImpl();

			Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
					userDetails.getPassword(), userDetails.getAuthorities());

			securityContext.setAuthentication(authentication);
			return securityContext;
		}

		return SecurityContextHolder.createEmptyContext();
	}

	private UserDetails loadUserDetails(HttpServletRequest request) {
		String username = tokenUtil.getUserName(request);
		return (username != null) ? userDetailsService.loadUserByUsername(username) : null;
	}

	@Override
	public void destroy() {

	}

	public TokenUtil getTokenUtil() {
		return tokenUtil;
	}

	public void setTokenUtil(TokenUtil tokenUtil) {
		this.tokenUtil = tokenUtil;
	}

	public CustomUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
