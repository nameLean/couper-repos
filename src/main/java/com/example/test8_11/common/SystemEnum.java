package com.example.test8_11.common;

import lombok.Getter;

@Getter
public enum SystemEnum {
    REPEAT_LOGIN(SystemConstant.REPEAT_LOGIN,"您的账号重复登录"),
    NO_DATA_FOR_ID(SystemConstant.SYSTEM_ERROR,"当前id没有对应的数据"),
    WEIXIN_NOBIND(SystemConstant.WEIXIN_NOBIND,"当前微信未绑定账号"),
    LOGIN_FAIL(SystemConstant.LOGIN_FAIL,"用户名或者密码错误"),
    JWT_EXPIRE(SystemConstant.SYSTEM_ERROR,"凭证过期"),

    REGISTER_USERNAME_FAIL(SystemConstant.REGISTER_USERNAME_FAIL,"用户名已经存在"),
    REGISTER_MOBIL_FAIL(SystemConstant.REGISTER_MOBIL_FAIL,"该电话已经被使用！请更换注册电话"),
    REGISTER_EMAIL_FAIL(SystemConstant.REGISTER_EMAIL_FAIL,"该邮箱已经被使用！请更换注册邮箱"),

    LOGIN_PARAM_ERROR(SystemConstant.SYSTEM_ERROR,"用户名与密码不能为空"),
    NO_permission(SystemConstant.NO_PERMISSION,"没有操作权限，请联系管理员"),
    REQUEST_FAIL(SystemConstant.SYSTEM_ERROR,"请求失败"),
    PARAM_EMPTY_ERROR(SystemConstant.SYSTEM_ERROR,"参数不能为空"),
    REQUEST_SUCCESS(SystemConstant.SYSTEM_SUCCESS,"请求成功"),
    LOGIN_EXPIRE(SystemConstant.NO_LOGIN,"登录超时请重新登录"),
    NO_LOGIN(SystemConstant.NO_LOGIN,"请先登录");
    private int code;
    private String msg;

    SystemEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
