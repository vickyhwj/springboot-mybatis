package com.winterchen.config.security;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.catalina.servlet4preview.http.HttpServletRequestWrapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyAnonymousFilter implements Filter {
    UserDetailsService userDetailsService;

    public MyAnonymousFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    public MyAnonymousFilter() {
        System.out.print("MyAnonymousFilterL:"+this);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest.getParameter("um") != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(servletRequest.getParameter("um"));
            List<GrantedAuthority> auths=new ArrayList<>();
            auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),auths);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        HttpServletRequest request=new HttpServletRequestWrapper((javax.servlet.http.HttpServletRequest) servletRequest){
            @Override
            public String getRequestURI() {
                return super.getRequestURI().replace("/jj","");
            }

            @Override
            public String getServletPath() {
                return super.getServletPath().replace("/jj","");
            }
        };
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
