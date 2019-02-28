package com.yotado.core.controller;

import com.yotado.core.config.annotation.WebLoginRequired;
import com.yotado.core.config.validator.AddGroup;
import com.yotado.core.model.IntoCargo;
import com.yotado.core.service.IntoCargoService;
import com.yotado.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web")
@CrossOrigin
public class IntoCargoController extends BaseController{

    @Autowired
    IntoCargoService intoCargoService;

    /**
     * 添加入库管理
     * POST /web/intoCargos
     * @param intoCargo
     */
    @WebLoginRequired
    @PostMapping("/intocargos")
    public Result createIntoCargos(@RequestBody @Validated(AddGroup.class) IntoCargo intoCargo){

        return Result.success(intoCargoService.createCargo(intoCargo));
    }
//
    @GetMapping("/getintocargos/list")
    public Result getIntoCargosList(){

        IntoCargo intoCargo = IntoCargo.builder().build();
        return Result.success(intoCargoService.getCategoryList(intoCargo));
    }


}
