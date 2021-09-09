package com.example.test8_11.common.exception;


import com.example.test8_11.common.SystemEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class MyException extends RuntimeException{
    private Map map;
    String msg;

    //构造方法
    public MyException(String msg){
        super(msg);
        this.msg = msg;
    }
    public MyException(SystemEnum systemEnum){
        map = new HashMap<>();
        map.put("code",systemEnum.getCode());
        map.put("msg",systemEnum.getMsg());
    }
    public MyException(Map map){
        this.map = map;
    }
}
