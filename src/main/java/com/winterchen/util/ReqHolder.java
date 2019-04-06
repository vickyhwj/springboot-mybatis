package com.winterchen.util
        ;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class ReqHolder {
    public static HttpServletRequest getRequest(){
        RequestAttributes requestAttributes= requestAttributes = RequestContextHolder.currentRequestAttributes();
        return ((ServletRequestAttributes)requestAttributes).getRequest();

    }
}
