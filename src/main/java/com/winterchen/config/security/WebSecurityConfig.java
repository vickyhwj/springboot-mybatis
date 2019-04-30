package com.winterchen.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;

@Order(1)
@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;

	@Autowired
	private UserDetailsService  userDetailsService;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private ResourceServerTokenServices resourceServerTokenServices;

	OAuth2AuthenticationManager oAuth2AuthenticationManager;

	@Bean
	PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	//配置全局设置
	@Autowired
	private void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//设置UserDetailsService以及密码规则
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		/*不需要授权的目录*/
		web.ignoring().antMatchers("/api/**");

	}
	
	@Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean() ;
    }
	
	//开启全局方法拦截
    @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
    public static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
        @Override
        protected MethodSecurityExpressionHandler createExpressionHandler() {
            return new OAuth2MethodSecurityExpressionHandler();
        }
    }
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()                    //  定义当需要用户登录时候，转到的登录页面。
				.and()
				.authorizeRequests()        // 定义哪些URL需要被保护、哪些不需要被保护
				.antMatchers("/user/**").hasRole("USER")     // 任何请求,登录后可以访问
				.antMatchers("/admin/**").hasRole("ADMIN");

		http.logout().logoutUrl("/logout").logoutSuccessHandler(customLogoutSuccessHandler);

		OAuth2AuthenticationProcessingFilter oAuth2AuthenticationProcessingFilter=new MyOauth2AuthProcessFilter();
		oAuth2AuthenticationProcessingFilter.setAuthenticationManager(oAuth2AuthenticationManager());
		http.csrf().disable();http.addFilterBefore(oAuth2AuthenticationProcessingFilter, RequestCacheAwareFilter.class);
		super.configure(http);
	}

	public OAuth2AuthenticationManager oAuth2AuthenticationManager(){
		OAuth2AuthenticationManager manager=new OAuth2AuthenticationManager();
		manager.setClientDetailsService(clientDetailsService);
		manager.setResourceId("aaa");
		manager.setTokenServices(resourceServerTokenServices);
		return manager;
	}
}
