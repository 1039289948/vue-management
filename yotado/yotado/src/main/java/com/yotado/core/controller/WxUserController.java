package com.yotado.core.controller;

import com.yotado.core.config.annotation.CurrentWxUser;
import com.yotado.core.config.annotation.WxLoginRequired;
import com.yotado.core.model.WxUser;
import com.yotado.core.service.WxUserService;
import com.yotado.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangle
 * @date 2018/11/29
 */

@RestController
@RequestMapping("/wx")
public class WxUserController extends BaseController{

    @Autowired
    WxUserService wxUserService;

    /**
     * 微信小程序登录
     * @param wxUser
     * @return
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody WxUser wxUser){
        return Result.success(wxUserService.login(wxUser));
    }

    @WxLoginRequired
    @GetMapping(value = "/current_user")
    public Result getCurrentUser(@CurrentWxUser WxUser wxUser){
        return Result.success(wxUser);
    }

}
