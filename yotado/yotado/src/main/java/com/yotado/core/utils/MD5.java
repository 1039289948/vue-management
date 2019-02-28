package com.yotado.core.utils;

import com.yotado.core.config.exception.MbyException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author wangle
 * @date 2019/1/8
 * @description md5签名和验证
 */

public class MD5 {

    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new MbyException("UTF-8 Is Unsupported");
        } catch (NoSuchAlgorithmException e) {
            throw new MbyException("MD5 Is Unsupported");
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 按参数名称升序，将参数值进行连接签名
     * @param appSecret
     * @param params
     * @return
     */
    public static String sign(String appSecret, TreeMap<String, String> params) {
        StringBuilder paramValues = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if(entry.getKey().equals("timestamp") || entry.getKey().equals("user_id")){
                paramValues.append(entry.getValue());
            }
        }
        paramValues.append(appSecret);
        String signStr = md5(paramValues.toString());
        return signStr;
    }

    /**
     * 请求参数签名验证
     * @param appSecret
     * @param request
     * @return
     * @throws Exception
     */
    public static void verifySign(String appSecret, HttpServletRequest request) throws Exception {
        TreeMap<String, String> params = new TreeMap<String, String>();
        String signStr = request.getHeader("sign");
        if(StringUtils.isBlank(signStr)){
            throw new MbyException("Signature Required");
        }
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement().trim();
            if (!paramName.equals("sign")) {
                params.put(paramName, URLDecoder.decode(request.getParameter(paramName), "UTF-8"));
            }
        }
        if (!sign(appSecret, params).equals(signStr)) {
            throw new MbyException("Sign Illegal");
        }
    }

    public static void main(String[] args) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put("brand_id", "1");
        params.put("timestamp", "1548825923");
        params.put("user_id", "27556");
        System.out.println(sign("MOBIYUN1807", params));
    }

}
