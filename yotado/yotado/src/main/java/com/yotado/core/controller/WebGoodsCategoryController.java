package com.yotado.core.controller;

import com.yotado.core.config.annotation.WebLoginRequired;
import com.yotado.core.model.GoodsCategory;
import com.yotado.core.model.GoodsCategoryPic;
import com.yotado.core.service.GoodsCategoryService;
import com.yotado.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangle
 * @date 2019/2/15
 * @description 商品品类
 */

@RestController
@RequestMapping("/web")
@CrossOrigin

public class WebGoodsCategoryController extends BaseController{

    @Autowired
    GoodsCategoryService goodsCategoryService;

    /**
     * 创建一个商品
     * POST /web/goods_categories
     * @param goodsCategory
     */
    @WebLoginRequired
    @PostMapping("/goods_categories")
    public Result createGoodsCategory(@RequestBody GoodsCategory goodsCategory){
        return Result.success(goodsCategoryService.createCategory(goodsCategory));
    }
    /**
    * 创建一个商品
    * GET /web/goods_categories
    * @param goodsType
     */
    @GetMapping("/goods_categories/list")
    public Result getGoodsCategoryList(String goodsType, String goodsName){
        GoodsCategory goodsCategory;
        if (goodsType == null && goodsName == null){
            goodsCategory = GoodsCategory.builder().build();
        } else {
            goodsCategory = GoodsCategory.builder()
                    .goodsType(goodsType)
                    .goodsName(goodsName)
                    .build();
        }
        return Result.success(goodsCategoryService.getGoodsCategoryList(goodsCategory));
    }







//    /**
//     * 获取商品品类列表
//     * GET /web/goods_categories
//     * @param name
//     * @param status
//     * @return
//     */
//    @WebLoginRequired
//    @GetMapping("/goods_categories")
//    public Result getCategoryList(String name, Integer status, Integer level, Integer parentId, Integer selected){
//        GoodsCategory goodsCategory = GoodsCategory.builder()
//                .name(name)
//                .status(status)
//                .level(level)
//                .parentId(parentId)
//                .selected(selected).build();
//        return Result.success(goodsCategoryService.getCategoryList(goodsCategory));
//    }

    /**
     * 删除一个商品品类
     * @param id
     * @return
     */
    @WebLoginRequired
    @DeleteMapping("/goods_categories/{id}")
    public Result deleteCategory(@PathVariable Long id){
        GoodsCategory goodsCategory = GoodsCategory.builder().id(id).build();
        goodsCategoryService.deleteCategory(goodsCategory);
        return Result.ok();
    }

    /**
     * 修改一个商品品类
     * @param id
     * @param goodsCategory
     * @return
     */
    @WebLoginRequired
    @PutMapping("/goods_categories/{id}")
    public Result updateCategory(@PathVariable Long id, @RequestBody GoodsCategory goodsCategory){
        goodsCategory.setId(id);
        goodsCategoryService.updateCategory(goodsCategory);
        return Result.ok();
    }

    @WebLoginRequired
    @PostMapping("/goods_categories_pics")
    public Result createCategoryPic(@RequestBody GoodsCategoryPic goodsCategoryPic){
        return Result.success(goodsCategoryService.createCategoryPic(goodsCategoryPic));
    }
}
