package com.yotado.core.utils;

import java.util.HashMap;

/**
 * @author wangle
 * @date 2018/11/29
 * @description 返回结果类
 */

public class Result extends HashMap<String, Object> {

    public Result() {
        put("code", 0);
        put("msg", "success");
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    public static Result ok() {
        Result result = new Result();
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.put("data", data);
        return result;
    }

}
