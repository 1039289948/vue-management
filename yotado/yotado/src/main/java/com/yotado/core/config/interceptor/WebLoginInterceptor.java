package com.yotado.core.config.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.yotado.core.config.annotation.WebLoginRequired;
import com.yotado.core.config.annotation.WxLoginRequired;
import com.yotado.core.config.exception.MbyException;
import com.yotado.core.config.property.SystemProperty;
import com.yotado.core.model.WebUser;
import com.yotado.core.model.WxUser;
import com.yotado.core.utils.Redis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author wangle
 * @date 2018/12/7
 * @description 处理需要登录的接口
 */
public class WebLoginInterceptor implements HandlerInterceptor {

    @Autowired
    SystemProperty systemProperty;

    @Autowired
    Redis redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        if (!(handler instanceof HandlerMethod)) { // 如果不是映射到方法直接通过
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        WebLoginRequired methodAnnotation = method.getAnnotation(WebLoginRequired.class); // 判断接口是否微信需要登录

        if (methodAnnotation != null) { // 有 @WebLoginRequired 注解，需要认证 : 判断是否存在令牌信息，如果存在，则允许登录
            String token = request.getHeader(systemProperty.getWebTokenName());
            if (StringUtils.isEmpty(token)) {
                throw new MbyException("Web Login Required");
            }else{
                String userString = redis.get(systemProperty.getTokenRedisPath() + token);
                if(StringUtils.isBlank(userString)){
                    throw new MbyException("Web Login Overtime");
                }
                WebUser webUser = JSONObject.parseObject(userString).toJavaObject(WebUser.class);
                request.setAttribute(systemProperty.getCurrentUser(), webUser);
            }
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
