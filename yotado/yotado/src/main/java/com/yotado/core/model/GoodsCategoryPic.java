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
 * @description 商品品类图片
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Table(name = "goods_category_pics")
public class GoodsCategoryPic {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private Long categoryId;
    private String url;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    
}
