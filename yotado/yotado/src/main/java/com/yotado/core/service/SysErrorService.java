package com.yotado.core.service;

import com.yotado.core.mapper.SysErrCodeMapper;
import com.yotado.core.model.SysErrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author wangle
 * @date 2018/11/29
 * @description 处理错误代码的service
 */

@Service
public class SysErrorService {

    @Autowired
    SysErrCodeMapper sysErrCodeMapper;

    @Transactional
    public SysErrCode initError(String code) {
        SysErrCode where = SysErrCode.builder().code(code).build();
        SysErrCode errCode = sysErrCodeMapper.selectOne(where);
        if(errCode != null){
            return errCode;
        }else{
            Date now = new Date();
            errCode = SysErrCode.builder().code(code).msg(code).createdAt(now).updatedAt(now).build();
            sysErrCodeMapper.insertSelective(errCode);
            return errCode;
        }
    }
}
