package com.yotado.core.controller;

import com.yotado.core.config.property.SystemProperty;
import com.yotado.core.utils.Redis;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangle
 * @date 2018/11/29
 * @description 基础controller，提供一些通用的小方法
 */

public class BaseController {

    @Autowired
    public SystemProperty systemProperty;

    @Autowired
    Redis redis;

}
