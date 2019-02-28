package com.yotado.core.config.interceptor;

import com.yotado.core.config.annotation.SignRequired;
import com.yotado.core.config.exception.MbyException;
import com.yotado.core.config.property.SystemProperty;
import com.yotado.core.model.WxUser;
import com.yotado.core.service.WxUserService;
import com.yotado.core.utils.Http;
import com.yotado.core.utils.MD5;
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
 * @date 2018/1/8
 * @description 验证要求签名的api的签名信息
 * 签名字段：timestamp, user_id
 */

public class SignInterceptor implements HandlerInterceptor {

    @Autowired
    SystemProperty systemProperty;

    @Autowired
    WxUserService userService;

    @Autowired
    private Redis redis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) { // 如果不是映射到方法直接通过
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        SignRequired methodAnnotation = method.getAnnotation(SignRequired.class); // 判断接口是否需要登录
        if (methodAnnotation != null) { // 有 @SignRequired 注解，需要签名

            checkDuplicateRequest(request);

            String userId = request.getParameter("user_id");
            if(StringUtils.isBlank(userId)){
                throw new MbyException("UserId Required");
            }
            WxUser where = WxUser.builder().id(Long.parseLong(userId)).build();
            WxUser user = userService.getUser(where);
            if(user == null){
                throw new MbyException("UserId Not Existent");
            }
            request.setAttribute("currentUser", user);

            return true;
        }else{
            return true;
        }
    }

    private void checkDuplicateRequest(HttpServletRequest request) throws Exception{
        String ip = Http.getIpAddress(request).replace(":", "");
        String timestamp = request.getParameter("timestamp");
        String key = ip + timestamp + request.getMethod() + request.getServletPath();
        System.out.println(key);
        if(!redis.exists(systemProperty.getRequestRecordRedisPath() + key)){
            MD5.verifySign(systemProperty.getSignKey(), request);
            redis.set(systemProperty.getRequestRecordRedisPath() + key, key, 30*24*60*60);
        }else{
            throw new MbyException("Duplicate Request");
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
