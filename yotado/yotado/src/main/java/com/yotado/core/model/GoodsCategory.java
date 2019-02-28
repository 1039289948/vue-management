package com.yotado.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author noah
 * @date 2019/2/16
 * @description 商品品类
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Table(name = "goods_categories")
public class GoodsCategory {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String goodsName;
    private Integer goodsStatus;
    private Double goodsLong;
    private Double goodsHeight;
    private Double goodsWidth;
    private Date updatedAt;
    private Date createdAt;
    private String goodsDescription;
    private String goodsPrice;
    private String goodsPics;
    private String goodsType;
    private String goodsSize;

}
