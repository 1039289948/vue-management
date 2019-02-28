package com.yotado.core.service;

import com.alibaba.fastjson.JSONObject;
import com.yotado.core.config.exception.MbyException;
import com.yotado.core.mapper.WxUserMapper;
import com.yotado.core.model.WxUser;
import com.yotado.core.utils.Security;
import lombok.extern.slf4j.Slf4j;
import net.dongliu.requests.Requests;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author wangle
 * @date 2018/11/29
 * @description 处理微信小程序用户业务的service
 */

@Slf4j
@Service
public class WxUserService extends BaseService {

    @Autowired
    WxUserMapper wxUserMapper;

    public WxUser login(WxUser wxUser) {
        if (StringUtils.isNotBlank(wxUser.getResCode())) {
            String url = systemProperty.getWeixinLoginApi()
                    + "appid=" + systemProperty.getXcxAppId()
                    + "&secret=" + systemProperty.getXcxAppSecret()
                    + "&js_code=" + wxUser.getResCode()
                    + "&grant_type=authorization_code";
            JSONObject result = Requests.get(url).send().readToJson(JSONObject.class);
            log.info(url);
            log.info(result.toString());
            if (result.containsKey("openid")) {
                WxUser whereUser = WxUser.builder().openId(result.getString("openid")).build();
                whereUser = wxUserMapper.selectOne(whereUser);
                wxUser.setOpenId(result.getString("openid"));
                wxUser.setSessionKey(result.getString("session_key"));
                if (whereUser == null) {
                    Date now = new Date();
                    wxUser.setCreatedAt(now);
                    wxUser.setUpdatedAt(now);
                    wxUserMapper.insert(wxUser);
                } else {
                    wxUser.setId(whereUser.getId());
                    wxUserMapper.updateByPrimaryKeySelective(wxUser);
                }
                sendToken(wxUser);
                return wxUser;
            } else {
                throw new MbyException("Weixin Login Failed");
            }
        } else {
            throw new MbyException("ResCode Required");
        }
    }

    private void sendToken(WxUser wxUser) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String token = Security.generate(wxUser.getOpenId() + System.currentTimeMillis());
        redis.set(systemProperty.getTokenRedisPath() + token, JSONObject.toJSONString(wxUser), 24 * 60 * 60);
        response.setHeader(systemProperty.getWxTokenName(), token);
    }

    public WxUser getUser(WxUser wxUser) {
        return wxUserMapper.selectOne(wxUser);
    }

}
