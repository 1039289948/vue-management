package com.yotado.core.config.exception;

import com.yotado.core.model.SysErrCode;
import com.yotado.core.service.SysErrorService;
import com.yotado.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangle
 * @date 2018/11/29
 * @description 自定义异常处理器
 */

@RestControllerAdvice
public class MbyExceptionHandler {

    @Autowired
    SysErrorService sysErrorService;

    @ExceptionHandler
    @ResponseBody
    public Result MbyExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex){
        Result result = new Result();
        response.setHeader("Access-Control-Allow-Origin", "*");
        if(ex instanceof MbyException) {
            MbyException e = (MbyException)ex;
            if(e.getCode() != null){
                result.put("code", e.getCode());
                result.put("msg",ex.getMessage());
            }else{
                SysErrCode error = sysErrorService.initError(e.getMessage());
                result.put("code", error.getId());
                result.put("msg", error.getMsg());
            }
            if(e.getDebugId() != null){
                result.put("debugId", e.getDebugId());
            }
        }else if("MethodArgumentNotValidException".equals(ex.getClass().getSimpleName())){ //验证字段异常.
            MethodArgumentNotValidException e = (MethodArgumentNotValidException)ex;
            String message = e.getMessage();
            String code = message.substring(message.indexOf("]]; default message [") + "]]; default message [".length(),message.length()-3); //fixme 此处获取验证字段错误信息不够优雅
            SysErrCode error = sysErrorService.initError(code);
            result.put("code", error.getId());
            result.put("msg", error.getMsg());
        }else{
            result.put("code",500);
            if(ex.getMessage() != null){
                result.put("msg",ex.getMessage());
            }
            result.put("excetion", ex.getClass().getSimpleName());
        }
        return result;
    }
}
