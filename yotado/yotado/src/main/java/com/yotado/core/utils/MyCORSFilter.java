package com.yotado.core.utils;

/**
 * Created by Jimmy on 2018/1/7.
 */

import com.yotado.core.config.property.SystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wangle on 2017/7/10.
 */

@Configuration
public class MyCORSFilter implements Filter {

    @Autowired
    SystemProperty systemProperty;

    @Override
    public void init(FilterConfig config){

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String origin = "*";
        if(request.getHeader("origin") != null){
            origin = request.getHeader("origin");
        }
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*, Content-Type, Origin, X-Mby20, X-Requested-With,ContentType,sign, Token-Yotado-Web");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Expose-Headers",systemProperty.getWebTokenName());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }



}

