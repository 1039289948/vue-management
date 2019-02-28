package com.yotado.core.config.interceptor;

import com.yotado.core.config.annotation.Session;
import com.yotado.core.model.WxUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangle
 * @date 2018/12/7
 * @description 获取当前session
 */

public class SessionResolver implements HandlerMethodArgumentResolver{

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(WxUser.class) && parameter.hasParameterAnnotation(Session.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest request, WebDataBinderFactory binderFactory){
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return httpServletRequest.getSession();
    }

}
