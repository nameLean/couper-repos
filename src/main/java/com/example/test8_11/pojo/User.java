package com.example.test8_11.pojo;

import com.example.test8_11.pojo.parameter.UserParam;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * user
 * @author 
 */
@Data
@Builder
public class User implements Serializable {
    private Integer id;

    private String userName;

    private String userPassword;

    /**
     * 头像
     */
    private String headImag;

    /**
     * 电话
     */
    private String userMobile;

    private String userEmail;

    private String userSex;
    //账号状态，0未激活，1可用
    private char user_status;

    private static final long serialVersionUID = 1L;

    public static User generateUserByUSerParam(UserParam userParam,String upload){
        User user=User.builder()
                .userName(userParam.getUsername())
                .userPassword(userParam.getPassword())
                .headImag(upload)
                .userEmail(userParam.getUserEmail())
                .userMobile(userParam.getUserMobile())
                .userSex(userParam.getUserSex()).build();
        return  user;
    }
}