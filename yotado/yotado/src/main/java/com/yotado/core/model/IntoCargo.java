package com.yotado.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Table(name = "into_management")

public class IntoCargo {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String goodsName;
    private String goodsType;
    private String userName;
    private String area;
    private String goodsId;
    private String goodsNumber;
    private Date updatedAt;
    private Date createdAt;

}
