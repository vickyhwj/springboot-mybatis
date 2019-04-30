package com.winterchen.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {
	
	private static final String BEARER_AUTHENTICATION = "Bearer";
	private static final String HEADER_AUTHORIZATION = "authorization";
	
	@Autowired
	private TokenStore tokenStore;

	OAuth2AuthenticationEntryPoint oAuth2AuthenticationEntryPoint=new OAuth2AuthenticationEntryPoint();

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String token = request.getHeader(HEADER_AUTHORIZATION);
		if (StringUtils.hasLength(token) && token.startsWith(BEARER_AUTHENTICATION)) {
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(token.split(" ")[1]);
			if (accessToken != null) {
				tokenStore.removeAccessToken(accessToken);
			}
			//response.setStatus(HttpServletResponse.SC_OK);
			response.setStatus(HttpServletResponse.SC_OK);		}
		else if(StringUtils.hasLength(request.getParameter("access_token"))){
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(request.getParameter("access_token"));
			tokenStore.removeAccessToken(accessToken);
			response.setStatus(HttpServletResponse.SC_OK);
		}
		else{
			response.sendRedirect("/");
		}

	}

}
