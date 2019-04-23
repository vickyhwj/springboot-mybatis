package com.winterchen.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.*;
import java.io.IOException;

import static org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager.authenticated;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	@Bean
	public CustomLogoutSuccessHandler customerLogoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}

	@Autowired
	UserDetailsService userDetailsService;


	@Autowired
	ResourceServerTokenServices tokenService;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		/*资源访问控制*/

		http.exceptionHandling()
		.authenticationEntryPoint(customAuthenticationEntryPoint)
			.and()
			.logout()
			.logoutUrl("/oauth/logout")
			.logoutSuccessHandler(customerLogoutSuccessHandler())
			.and()
			.authorizeRequests()
			.antMatchers("/user/**").permitAll();
		//	.antMatchers("/user/**").hasRole("ADMIN");
		http.addFilterAt(new MyAnonymousFilter(userDetailsService), AnonymousAuthenticationFilter.class);

	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(tokenService);
	}
}
