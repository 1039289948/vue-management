package com.yotado.core.service;

import com.yotado.core.mapper.IntoCargoMapper;
import com.yotado.core.model.IntoCargo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class IntoCargoService extends BaseService {

    @Autowired
    IntoCargoMapper intoCargoMapper;

    /**
     * 创建入库订单
     * @param intoCargo
     * @return
     */
    @Transactional
    public IntoCargo createCargo(IntoCargo intoCargo){
        intoCargo.setGoodsName(intoCargo.getGoodsName());
        intoCargo.setGoodsType(intoCargo.getGoodsType());
        intoCargo.setArea(intoCargo.getArea());
        intoCargo.setGoodsNumber(intoCargo.getGoodsNumber());
        intoCargo.setUserName(intoCargo.getUserName());
        Date now = new Date();
        intoCargo.setCreatedAt(now);
        intoCargo.setUpdatedAt(now);
        String goods_id = intoCargo.getGoodsType() + now.getTime();
        intoCargo.setGoodsId(goods_id);
        intoCargoMapper.insertSelective(intoCargo);
        return intoCargo;
    }

    /**
     * 获取入库列表
     * @param intoCargo
     * @return
     */
    public List<IntoCargo> getCategoryList(IntoCargo intoCargo){
        Example example = new Example(IntoCargo.class);
        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("carriersPhone", intoCargo.getCarriersPhone());
//        criteria.andEqualTo("carriersName", intoCargo.getCarriersName());
//        criteria.andEqualTo("customUser", intoCargo.getCustomUser());
//        criteria.andEqualTo("days", intoCargo.getDays());
//        criteria.andEqualTo("grossWeight", intoCargo.getGrossWeight());
//        criteria.andEqualTo("jobName", intoCargo.getJobName());
//        criteria.andEqualTo("name", intoCargo.getName());
//        criteria.andEqualTo("createdAt", intoCargo.getCreatedAt());
//        criteria.andEqualTo("id", intoCargo.getId());
        return intoCargoMapper.selectByExample(example);
    }
}
