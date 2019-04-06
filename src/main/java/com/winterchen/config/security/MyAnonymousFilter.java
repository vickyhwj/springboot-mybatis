package com.winterchen.config.security;

import com.winterchen.util.ReqHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyAnonymousFilter implements Filter {
    UserDetailsService userDetailsService;

    public MyAnonymousFilter(UserDetailsService userDetailsService) {
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
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
