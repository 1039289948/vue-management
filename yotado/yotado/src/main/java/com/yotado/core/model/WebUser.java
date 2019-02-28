package com.yotado.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "web_users")
public class WebUser {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String loginName;
    private String password;
    private Date createdAt;
    private Date updatedAt;

    @Transient
    private String verifyCode;

}
