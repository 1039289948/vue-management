package com.yotado.core.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangle
 * @date 2018/11/29
 * @description 系统配置属性
 */

@Setter
@Getter
@Component
@ConfigurationProperties(prefix="system")
public class SystemProperty {

    private String signKey;

    private String xcxAppId;
    private String xcxAppSecret;
    private String weixinLoginApi;

    private String requestRecordRedisPath;
    private String userErrorCountRedisPath;
    private String userVerifyCodeRedisPath;
    private String tokenRedisPath;
    private String verifyImageDir;

    private String wxTokenName;
    private String webTokenName;

    private String currentUser;

}
