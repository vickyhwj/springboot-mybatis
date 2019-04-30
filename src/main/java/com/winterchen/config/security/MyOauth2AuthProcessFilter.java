package com.winterchen.config.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class MyOauth2AuthProcessFilter extends OAuth2AuthenticationProcessingFilter{
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if(SecurityContextHolder.getContext().getAuthentication()==null) {
            super.doFilter(req, res, chain);
        }
        else{
            chain.doFilter(req,res);
        }
    }
}
