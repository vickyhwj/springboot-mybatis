package com.winterchen.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//@Order(1)
//@Configuration
//@EnableResourceServer
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
		List accessDecisionVoters=new ArrayList<>();
		accessDecisionVoters.add(new RoleVoter());
		AccessDecisionManager manager=new AffirmativeBased(accessDecisionVoters);

		/*http.exceptionHandling()
		.authenticationEntryPoint(customAuthenticationEntryPoint)
			.and()
			.logout()
			.logoutUrl("/oauth/logout")
			.logoutSuccessHandler(customerLogoutSuccessHandler())
			.and()
			.authorizeRequests()
			.antMatchers("/user1/**").hasRole("ADMIN");*/
		//	.antMatchers("/user/**").hasRole("ADMIN");
		http.addFilterBefore(new MyAnonymousFilter(userDetailsService), AnonymousAuthenticationFilter.class);
		http.addFilterBefore(new Filter() {
			@Override
			public void init(FilterConfig filterConfig) throws ServletException {

			}

			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
				System.out.println("beforeMyAnonymousFilter");
				chain.doFilter(request,response);
			}

			@Override
			public void destroy() {

			}
		},MyAnonymousFilter.class);

		http
				.authorizeRequests()
				.antMatchers("/jj/**").authenticated()
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					public <O extends FilterSecurityInterceptor> O postProcess(
							O fsi) {
						fsi.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource());
						fsi.setAccessDecisionManager(manager);
						return fsi;
					}
				});

	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(tokenService);


	}

	@Bean
	MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource(){
		return new MyFilterInvocationSecurityMetadataSource();
	}


}
