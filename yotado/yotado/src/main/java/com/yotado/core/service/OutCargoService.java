package com.yotado.core.service;
import com.yotado.core.mapper.OutCargoMapper;
import com.yotado.core.model.OutCargo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Slf4j
@Service

public class OutCargoService extends BaseService{
    @Autowired
    OutCargoMapper outCargoMapper;

    /**
     * 创建出库订单
     * @param outCargo
     * @return
     */
    @Transactional
    public OutCargo createOutCargo(OutCargo outCargo){
        outCargo.setCargoName(outCargo.getCarriersName());
        outCargo.setCarriersName(outCargo.getCarriersName());
        outCargo.setCarriersPhone(outCargo.getCarriersPhone());
        outCargo.setCustomUser(outCargo.getCustomUser());
        outCargo.setGrossWeight(outCargo.getGrossWeight());
        outCargo.setOrgnaizationName(outCargo.getOrgnaizationName());
        outCargo.setOutMoney(outCargo.getOutMoney());
        Date now = new Date();
        outCargo.setCreatedAt(now);
        outCargoMapper.insertSelective(outCargo);
        return outCargo;
    }
    /**
     * 获取出库列表
     * @param outCargo
     * @return
     */
    public List<OutCargo> getOutCategoryList(OutCargo outCargo){
        Example example = new Example(OutCargo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("carriersPhone", outCargo.getCarriersPhone());
        criteria.andEqualTo("carriersName", outCargo.getCarriersName());
        criteria.andEqualTo("customUser", outCargo.getCustomUser());
        criteria.andEqualTo("outMoney", outCargo.getOutMoney());
        criteria.andEqualTo("grossWeight", outCargo.getGrossWeight());
        criteria.andEqualTo("cargoName", outCargo.getCargoName());
        criteria.andEqualTo("orgnaizationName", outCargo.getOrgnaizationName());
        criteria.andEqualTo("createdAt", outCargo.getCreatedAt());
        criteria.andEqualTo("id", outCargo.getId());
        return outCargoMapper.selectByExample(example);
    }
}
