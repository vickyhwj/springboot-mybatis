package com.winterchen.config.security;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerTokenServicesConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableConfigurationProperties(AuthorizationServerProperties.class)
@Import(AuthorizationServerTokenServicesConfiguration.class)
public class OAuth2FormAuthenticationConfiguration extends OAuth2AuthorizationServerConfiguration {
	

	public OAuth2FormAuthenticationConfiguration(BaseClientDetails details,
			AuthenticationConfiguration authenticationConfiguration,
			ObjectProvider<TokenStore> tokenStore,
			ObjectProvider<AccessTokenConverter> tokenConverter,
			AuthorizationServerProperties properties) throws Exception {
		super(details, authenticationConfiguration, tokenStore, tokenConverter, properties);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		super.configure(clients);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		super.configure(endpoints);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security)
			throws Exception {
		super.configure(security);
		security.allowFormAuthenticationForClients();
	}
}
