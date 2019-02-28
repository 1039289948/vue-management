package com.yotado.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "wx_users")
public class WxUser {

    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    private String openId;
    private String resCode;
    private String sessionKey;
    private String nickName;
    private String avatarUrl;
    private Integer gender;
    private String country;
    private String province;
    private String city;
    private Date createdAt;
    private Date updatedAt;

}
