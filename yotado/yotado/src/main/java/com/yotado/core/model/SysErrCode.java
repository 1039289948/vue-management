package com.yotado.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wangle
 * @date 2018/11/29
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Table(name = "sys_err_codes")
public class SysErrCode implements Serializable {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String code;
    private String msg;
    private Date createdAt;
    private Date updatedAt;

}