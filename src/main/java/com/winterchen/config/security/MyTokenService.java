package com.winterchen.config.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

public class MyTokenService extends DefaultTokenServices {
    UserDetailsService userDetailsService;

    @Override
    public OAuth2Authentication loadAuthentication(String accessTokenValue) throws AuthenticationException, InvalidTokenException {
//        if(ReqHolder.getRequest().getParameter("um")!=null){
//            UserDetails userDetails= userDetailsService.loadUserByUsername(ReqHolder.getRequest().getParameter("um"));
//            Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
//            OAuth2Authentication oAuth2Authentication=new OAuth2Authentication(null,authentication);
//            return oAuth2Authentication;
//        }
        return super.loadAuthentication(accessTokenValue);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
