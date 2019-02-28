package com.yotado.core.service;

import com.yotado.core.enums.SystemEnum;
import com.yotado.core.mapper.GoodsCategoryMapper;
import com.yotado.core.mapper.GoodsCategoryPicMapper;
import com.yotado.core.model.GoodsCategory;
import com.yotado.core.model.GoodsCategoryPic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author wangle
 * @date 2018/11/29
 */

@Slf4j
@Service
public class GoodsCategoryService extends BaseService {

    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    GoodsCategoryPicMapper goodsCategoryPicMapper;

    /**
     * 创建一个新的商品分类
     * @param goodsCategory
     * @return
     */
    @Transactional
    public GoodsCategory createCategory(GoodsCategory goodsCategory){
        GoodsCategory where = GoodsCategory.builder().goodsName(goodsCategory.getGoodsName()).goodsType(goodsCategory.getGoodsType()).build();
        int count = goodsCategoryMapper.selectCount(where);
        if(count > 0){
            respErr("goodsName or goodsType Is Existent");
        }
        Date now = new Date();
        goodsCategory.setGoodsType(goodsCategory.getGoodsType());
        goodsCategory.setGoodsPrice(goodsCategory.getGoodsPrice());
        goodsCategory.setGoodsPics(goodsCategory.getGoodsPics());
        goodsCategory.setGoodsName(goodsCategory.getGoodsName());
        goodsCategory.setGoodsLong(goodsCategory.getGoodsLong());
        goodsCategory.setGoodsHeight(goodsCategory.getGoodsHeight());
        goodsCategory.setGoodsWidth(goodsCategory.getGoodsWidth());
        goodsCategory.setGoodsDescription(goodsCategory.getGoodsDescription());
        goodsCategory.setGoodsStatus(goodsCategory.getGoodsStatus());
        goodsCategory.setCreatedAt(now);
        goodsCategory.setUpdatedAt(now);
        String goods_sizes = goodsCategory.getGoodsLong() + "*" + goodsCategory.getGoodsWidth() + "*" + goodsCategory.getGoodsHeight();
        goodsCategory.setGoodsSize(goods_sizes);
        goodsCategoryMapper.insertSelective(goodsCategory);
        return goodsCategory;
    }

    /**
     * 获取出库列表
     * @param goodsCategory
     * @return
     */
    public List<GoodsCategory> getGoodsCategoryList(GoodsCategory goodsCategory){
        Example example = new Example(GoodsCategory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("goodsName", goodsCategory.getGoodsName());
        criteria.andEqualTo("goodsStatus", goodsCategory.getGoodsStatus());
        criteria.andEqualTo("goodsLong", goodsCategory.getGoodsLong());
        criteria.andEqualTo("goodsHeight", goodsCategory.getGoodsHeight());
        criteria.andEqualTo("goodsWidth", goodsCategory.getGoodsWidth());
        criteria.andEqualTo("goodsDescription", goodsCategory.getGoodsDescription());
        criteria.andEqualTo("goodsPrice", goodsCategory.getGoodsPrice());
        criteria.andEqualTo("createdAt", goodsCategory.getCreatedAt());
        criteria.andEqualTo("goodsPics", goodsCategory.getGoodsPics());
        criteria.andEqualTo("goodsType", goodsCategory.getGoodsType());
        criteria.andEqualTo("id", goodsCategory.getId());
        criteria.andEqualTo("goodsSize", goodsCategory.getGoodsSize());

        return goodsCategoryMapper.selectByExample(example);
    }

//    /**
//     * 获取商品品类列表
//     * @param goodsCategory
//     * @return
//     */
//    public List<GoodsCategory> getCategoryList(GoodsCategory goodsCategory){
//        Example example = new Example(GoodsCategory.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("status", goodsCategory.getStatus());
//        criteria.andEqualTo("level", goodsCategory.getLevel());
//        criteria.andEqualTo("parentId", goodsCategory.getParentId());
//        criteria.andEqualTo("selected", goodsCategory.getSelected());
//        criteria.andLike("name", "%" + goodsCategory.getName() + "%");
//        return goodsCategoryMapper.selectByExample(example);
//    }

    @Transactional
    public void deleteCategory(GoodsCategory goodsCategory){
        goodsCategory.setGoodsStatus(SystemEnum.STATUS_DELETED.getCode());
        goodsCategory.setUpdatedAt(new Date());
        goodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory);
    }

    @Transactional
    public void updateCategory(GoodsCategory goodsCategory){
        goodsCategory.setUpdatedAt(new Date());
        goodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory);
    }

    @Transactional
    public GoodsCategoryPic createCategoryPic(GoodsCategoryPic goodsCategoryPic){
        GoodsCategoryPic where = GoodsCategoryPic.builder().categoryId(goodsCategoryPic.getCategoryId()).name(goodsCategoryPic.getName()).build();
        int count;
        Date now = new Date();
        goodsCategoryPic.setCreatedAt(now);
        goodsCategoryPic.setUpdatedAt(now);
        goodsCategoryPicMapper.insertSelective(goodsCategoryPic);
        return goodsCategoryPic;
    }

}
