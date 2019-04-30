package com.winterchen.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthenticationConfiguration extends AuthorizationServerConfigurerAdapter {
	
	public AuthenticationConfiguration() {
		
	}


	
	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

//	@Bean
//	protected UserDetailsService userDetailsService() {
//		// @formatter:off
//		return new JdbcUserDetailsManager(dataSource);
//		// @formatter:on
//	}
	
	@Bean
	public TokenStore getTokenStore() {
		InMemoryTokenStore inMemoryTokenStore=new InMemoryTokenStore();
		return inMemoryTokenStore;

	}
	

	@Bean
	public UserDetailsService getUserDetailsService(){
		InMemoryUserDetailsManager inMemoryUserDetailsManager=new InMemoryUserDetailsManager();
		List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		User user=new User("vicky","123",grantedAuthorities);

		inMemoryUserDetailsManager.createUser(user);
		return inMemoryUserDetailsManager;
	}

	@Bean
	public MyTokenService getMyTokenService(){
		MyTokenService  tokenService=new MyTokenService();
		tokenService.setUserDetailsService(userDetailsService);
		tokenService.setAccessTokenValiditySeconds(99999999);
		tokenService.setTokenStore(getTokenStore());
		return tokenService;
	}

	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints.addInterceptor(new HandlerInterceptor() {
			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
					Exception ex) throws Exception {
				// TODO Auto-generated method stub
				System.out.println(request);
			}
		});



    	endpoints.tokenServices(getMyTokenService())
    		//.tokenStore(getTokenStore())
    		/*使用密碼模式的時候需要此項*/
    		.authenticationManager(authenticationManager)
    		/*使用refresh_token需要添加此配置，否則無法寫入數據庫*/
    		.userDetailsService(getUserDetailsService())
    		/*允許的接口訪問類型*/
    		.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }
	
//	@Override
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//		// access_token有效時長
//		clients.inMemory().withClient("erp").accessTokenValiditySeconds(43200);
//	}

}
