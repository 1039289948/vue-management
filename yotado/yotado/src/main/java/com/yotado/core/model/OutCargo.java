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
@Table(name = "out_management")

public class OutCargo {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String cargoName;
    private Date createdAt;
    private String orgnaizationName;
    private String customUser;
    private String carriersName;
    private String carriersPhone;
    private String grossWeight;
    private String outMoney;

}
