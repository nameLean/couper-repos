package com.example.test8_11.pojo.parameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
/*
这里的参数校验只需要校验那些前端传过来的，需要校验数据可靠性的数据
* */

@Data
@AllArgsConstructor
@NoArgsConstructor
//@ApiModel
public class UserParam {

    @NotBlank(message = "用户名不能为空")
    @Length(min = 5, max = 15, message = "用户名长度必须在5-15之间")
    //@ApiModelProperty(name = "username", value = "用户账号", required = true, example = "zhangsan", dataType = "string")
    private String username;

    //@ApiModelProperty(name = "password", value = "用户密码", required = true, example = "123123", dataType = "string")
    @NotBlank(message = "密码不能为空")
    @Length(min = 5, max = 15, message = "密码长度必须在5-15之间")
    private String password;

    //电话
    private String userMobile;

    @Email
    private String userEmail;

    private String userSex;

    //头像
    private MultipartFile headImag;
}
