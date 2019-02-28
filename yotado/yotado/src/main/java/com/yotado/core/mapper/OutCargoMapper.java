package com.yotado.core.mapper;

import com.yotado.core.model.OutCargo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
@org.apache.ibatis.annotations.Mapper
public interface OutCargoMapper extends Mapper<OutCargo> {

}
