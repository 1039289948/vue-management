package com.yotado.core.controller;

import com.qiniu.util.Auth;
import com.yotado.core.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangle
 * @date 2019/2/18
 * @description 七牛
 */

@RestController
@RequestMapping("/sys")
public class QiniuController {

    private static String accessKey = "YR3i8ymVDTx_0DfIwiYupWoKdD04AUezsHHFtB0v";
    private static String secretKey = "RK4qbc5t3j77jbEM_i4CEnSXtLl3J-mYRqaTeRJV";

    /**
     * 获取七牛上传文件token
     * get /qiniu/token?bucket=live&key=213
     * @throws Exception
     */
    @GetMapping(value = "/qiniu/token")
    public Result initWelcomeWords(String bucket, String key){
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucket, key);
        return Result.success(token);
    }

}
