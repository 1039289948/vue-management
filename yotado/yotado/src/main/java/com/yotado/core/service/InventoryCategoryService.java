package com.yotado.core.service;


import com.yotado.core.mapper.InventoryCategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InventoryCategoryService extends BaseService {

    @Autowired
    InventoryCategoryMapper inventoryCategoryMapper;



}
