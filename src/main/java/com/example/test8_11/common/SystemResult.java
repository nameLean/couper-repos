package com.example.test8_11.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class SystemResult<T> {
    private int code;
    private String msg;
    private T data;//携带的数据

    private SystemResult(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private SystemResult(int code, String msg, T data){
        this(code,msg);
        this.data = data;
    }

    private SystemResult(SystemEnum requestFail) {
        this(requestFail.getCode(),requestFail.getMsg());
    }

    //失败
    public static SystemResult fail(String msg){
        return new SystemResult(SystemConstant.SYSTEM_ERROR,msg);
    }
    public static SystemResult fail(int code,String msg){
        return new SystemResult(code,msg);
    }
    public static SystemResult fail(){
        return new SystemResult(SystemEnum.REQUEST_FAIL);
    }
    public static SystemResult fail(SystemEnum systemEnum){
        return new SystemResult(systemEnum);
    }
    public static SystemResult success(SystemEnum systemEnum){
        return new SystemResult(systemEnum);
    }
    public static SystemResult success(){
        return new SystemResult(SystemEnum.REQUEST_SUCCESS);
    }
    public static <T>SystemResult success(T data){
        SystemResult success = success();
        success.setData(data);
        return success;
    }
    @JsonIgnore
    public boolean isSuccess(){
        return  this.code == SystemConstant.SYSTEM_SUCCESS;
    }
}

