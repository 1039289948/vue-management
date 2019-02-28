package com.yotado.core.config.exception;

import com.yotado.core.model.SysErrCode;

/**
 * @author wangle
 * @date 2018/11/29
 * @description 自定义异常
 */

public class MbyException extends RuntimeException {

    private static final long serialVersionUID = -7638041501183925225L;

    private Integer code;
    private String exceptionInfo;
    private Integer debugId;

    public MbyException(String errorCode) {
        super(errorCode.toString());
    }

    public MbyException(SysErrCode error) {
        super(error.getMsg());
        this.code = error.getId();
    }

    public MbyException(String exceptionInfo, Integer debugId, Object data) {
        super(exceptionInfo);
        this.exceptionInfo = exceptionInfo;
        this.debugId = debugId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getDebugId() {
        return debugId;
    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
}
