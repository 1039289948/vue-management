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
@Table(name = "inventory_management")
public class InventoryCategory {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String jobName;
    private String days;
    private Date createdAt;
    private String name;
    private String customUser;
    private String carriersName;
    private String carriersPhone;
    private String grossWeight;
}
