package com.yotado.core.config.interceptor;

import com.yotado.core.config.annotation.CurrentWebUser;
import com.yotado.core.config.exception.MbyException;
import com.yotado.core.config.property.SystemProperty;
import com.yotado.core.model.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author wangle
 * @date 2018/11/29
 * @description 获取当前登录用户
 */

public class CurrentWebUserResolver implements HandlerMethodArgumentResolver{

    @Autowired
    SystemProperty systemProperty;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(WebUser.class) && parameter.hasParameterAnnotation(CurrentWebUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
        WebUser webUser = (WebUser) request.getAttribute(systemProperty.getCurrentUser(), RequestAttributes.SCOPE_REQUEST);
        if (webUser != null) {
            return webUser;
        }
        throw new MbyException("No Current WebUser");
    }
}
