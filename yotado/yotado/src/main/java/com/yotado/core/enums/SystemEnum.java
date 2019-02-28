package com.yotado.core.enums;

/**
 * @author wangle
 * @date 2018/12/15
 * @description 需要脱敏的数据类型
 */

public enum SystemEnum {

    STATUS_VALID(1,"有效的"),
    STATUS_DELETED(0,"已删除"),
    ;

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    SystemEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
