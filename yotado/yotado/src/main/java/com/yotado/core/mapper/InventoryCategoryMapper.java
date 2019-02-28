package com.yotado.core.mapper;


import com.yotado.core.model.InventoryCategory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

@Service
@org.apache.ibatis.annotations.Mapper
public interface InventoryCategoryMapper extends Mapper<InventoryCategory>{
}
