package com.yotado.core.controller;

import com.yotado.core.config.annotation.CurrentWebUser;
import com.yotado.core.config.annotation.CurrentWxUser;
import com.yotado.core.config.annotation.Session;
import com.yotado.core.config.annotation.WebLoginRequired;
import com.yotado.core.config.validator.AddGroup;
import com.yotado.core.model.WebUser;
import com.yotado.core.service.WebUserService;
import com.yotado.core.utils.Result;
import com.yotado.core.utils.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author wangle
 * @date 2019/2/15
 */
@RestController
@RequestMapping("/web")
@CrossOrigin
public class WebUserController extends BaseController{

    @Autowired
    WebUserService webUserService;

    /**
     * web后台用户注册
     * POST /web/users
     * @param webUser
     */
    @PostMapping("/users")
    public Result createUser(@RequestBody @Validated(AddGroup.class) WebUser webUser){

        return Result.success(webUserService.createUser(webUser));
    }
    /**
     * web后台用户登录
     * POST /web/login
     * @param webUser
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody WebUser webUser, @Session HttpSession session){
        return Result.success(webUserService.login(webUser, session));
    }

    @GetMapping("/verify_images")
    public void getVerifyImage(@Session HttpSession session) throws Exception{
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String verifyCode = VerificationCode.generateVerifyCodeBufferedImage(systemProperty.getVerifyImageDir(), response);
        redis.set(systemProperty.getUserVerifyCodeRedisPath() + session.getId(), verifyCode, 300);
    }

    @WebLoginRequired
    @PostMapping(value = "/logout")
    public Result logout(@CurrentWebUser WebUser webUser){
        webUserService.logout();
        return Result.ok();
    }

    @WebLoginRequired
    @GetMapping(value = "/current_user")
    public Result getCurrentUser(@CurrentWebUser WebUser webUser){
        return Result.success(webUser);
    }

}
