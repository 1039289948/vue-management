package com.yotado.core.service;

import com.alibaba.fastjson.JSONObject;
import com.yotado.core.mapper.WebUserMapper;
import com.yotado.core.model.WebUser;
import com.yotado.core.model.WxUser;
import com.yotado.core.utils.Security;
import com.yotado.core.utils.VerificationCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author wangle
 * @date 2018/11/29
 * @description 处理web后台用户业务的service
 */

@Slf4j
@Service
public class WebUserService extends BaseService {

    @Autowired
    WebUserMapper webUserMapper;

    /**
     * 注册新用户
     * @param webUser
     * @return
     */
    @Transactional
    public WebUser createUser(WebUser webUser){
        WebUser where = WebUser.builder().loginName(webUser.getLoginName()).build();
        int count = webUserMapper.selectCount(where);
        if(count > 0){
            respErr("LoginName Is Existent");
        }
        webUser.setPassword(Security.generate(webUser.getPassword()));
        Date now = new Date();
        webUser.setCreatedAt(now);
        webUser.setUpdatedAt(now);
        webUserMapper.insertSelective(webUser);
        return webUser;
    }

    /**
     * 用户登出
     */
    public void logout(){
        deleteToken();
    }

    /**
     * 用户登录
     * @param webUser
     * @param session
     * @return
     */
    public WebUser login(WebUser webUser, HttpSession session) {
        WebUser whereUser = WebUser.builder().loginName(webUser.getLoginName()).build();
        WebUser userDb = webUserMapper.selectOne(whereUser);
        if(userDb == null){
            respErr("LoginName Is Not Existent");
        }
//        checkVerifyCode(webUser, userDb, session);
        checkPassword(webUser, userDb, session);
        redis.del(systemProperty.getUserErrorCountRedisPath() + userDb.getId());
        sendToken(userDb);
        return userDb;
    }

    /**
     * 如果用户输入错误密码超过三次，则验证码
     * @param webUser
     * @param userDb
     */
    private void checkVerifyCode(WebUser webUser, WebUser userDb, HttpSession session){
        int errorCount = redis.getInt(systemProperty.getUserErrorCountRedisPath() + userDb.getId());
        if(errorCount >= 3){
            if(StringUtils.isBlank(webUser.getVerifyCode())){
                refreshVerifyCode(session);
                respErr("Need VerifyCode");
            }
            String verifyCode = redis.get(systemProperty.getUserVerifyCodeRedisPath() + session.getId());
            if(verifyCode == null){
                refreshVerifyCode(session);
                respErr("VerifyCode Is Overdue");
            }
            if(!verifyCode.equals(webUser.getVerifyCode())){
                refreshVerifyCode(session);
                respErr("VerifyCode Is Not Correct");
            }
        }
    }

    /**
     * 检查用户密码是否正确，如果不对，则更新并检查密码错误次数，如果错误次数有3次了，则提示错误已达三次，下次需要验证码了
     * @param webUser
     * @param userDb
     */
    private void checkPassword(WebUser webUser, WebUser userDb, HttpSession session){
        if(!Security.verify(webUser.getPassword(),userDb.getPassword())){
            Long errorCount = redis.increment(systemProperty.getUserErrorCountRedisPath() + userDb.getId());
            if(errorCount == 3){
                respErr("Three Times");
            }
            if(errorCount >= 3){
                refreshVerifyCode(session);
            }
            respErr("Password Is Not Correct");
        }
    }

    private void refreshVerifyCode(HttpSession session){
        String verifyCode = VerificationCode.generateVerifyCode();
        redis.set(systemProperty.getUserVerifyCodeRedisPath() + session.getId(), verifyCode, 300);
    }

    private void sendToken(WebUser webUser) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String token = Security.generate(webUser.getLoginName() + System.currentTimeMillis());
        redis.set(systemProperty.getTokenRedisPath() + token, JSONObject.toJSONString(webUser), 24 * 60 * 60);
        response.setHeader(systemProperty.getWebTokenName(), token);
    }

    private void deleteToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(systemProperty.getWebTokenName());
        redis.del(systemProperty.getTokenRedisPath() + token);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setHeader(systemProperty.getWebTokenName(), null);
    }

}
