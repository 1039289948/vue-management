package com.yotado.core.controller;

import com.yotado.core.config.validator.AddGroup;
import com.yotado.core.model.OutCargo;
import com.yotado.core.service.OutCargoService;
import com.yotado.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web")
@CrossOrigin

public class OutCargoController extends BaseController{

    @Autowired
    OutCargoService outCargoService;

    /**
     * 添加入库管理
     * POST /web/outcargos/add
     * @param outCargo
     */
    @PostMapping("/outcargos/add")
    public Result createOutCargos(@RequestBody @Validated(AddGroup.class) OutCargo outCargo){

        return Result.success(outCargoService.createOutCargo(outCargo));
    }
//
    /**
     * 添加入库管理
     * GET /web/getoutcargos/list
     */
    @GetMapping("/getoutcargos/list")
    public Result getOutCargosList(){
        OutCargo intoCargo = OutCargo.builder().build();
        return Result.success(outCargoService.getOutCategoryList(intoCargo));
    }

}
