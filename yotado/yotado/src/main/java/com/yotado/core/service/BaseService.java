package com.yotado.core.service;

import com.yotado.core.config.exception.MbyException;
import com.yotado.core.config.property.SystemProperty;
import com.yotado.core.utils.Redis;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangle
 * @date 2018/11/29
 * @description 基础service，提供一些通用的小方法
 */

public class BaseService {

    @Autowired
    SystemProperty systemProperty;

    @Autowired
    Redis redis;

    public void respErr(String errCode){
        throw new MbyException(errCode);
    }

}
