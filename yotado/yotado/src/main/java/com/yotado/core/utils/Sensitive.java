package com.yotado.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wangle
 * @date 2018/12/15
 * @description 不同类型的数据脱敏处理方法
 */

public class Sensitive {

    public static String mobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return "";
        }
        return StringUtils.left(mobile, 3).concat(
                StringUtils.removeStart(
                        StringUtils.leftPad(
                                StringUtils.right(mobile, 4), StringUtils.length(mobile), "*"),"****"));
    }

    public static String password(String password) {
        if (StringUtils.isBlank(password)) {
            return "";
        }
        return "******";
    }
}
