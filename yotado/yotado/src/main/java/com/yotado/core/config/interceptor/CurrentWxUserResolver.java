package com.yotado.core.config.interceptor;

import com.yotado.core.config.annotation.CurrentWxUser;
import com.yotado.core.config.exception.MbyException;
import com.yotado.core.config.property.SystemProperty;
import com.yotado.core.model.WxUser;
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

public class CurrentWxUserResolver implements HandlerMethodArgumentResolver{

    @Autowired
    SystemProperty systemProperty;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(WxUser.class) && parameter.hasParameterAnnotation(CurrentWxUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {
        WxUser wxUser = (WxUser) request.getAttribute(systemProperty.getCurrentUser(), RequestAttributes.SCOPE_REQUEST);
        if (wxUser != null) {
            return wxUser;
        }
        throw new MbyException("No Current WxUser");
    }
}
