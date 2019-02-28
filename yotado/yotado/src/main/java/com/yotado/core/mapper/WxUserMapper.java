package com.yotado.core.mapper;

import com.yotado.core.model.WxUser;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wangle
 * @date 2018/11/29
 */

@Service
@org.apache.ibatis.annotations.Mapper
public interface WxUserMapper extends Mapper<WxUser> {

}