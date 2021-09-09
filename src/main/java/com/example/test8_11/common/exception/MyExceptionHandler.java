package com.example.test8_11.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//自动检测带有@RestController注解的方法体中是否有异常抛出
@RestControllerAdvice//增强，ControllerAdvice+responsebody异常返回json格式
@Slf4j
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String exception(Exception e){
        log.error("项目出现异常:{}",e);
        //发邮箱
        return "项目出异常了";
    }

    @ExceptionHandler(MyException.class)//自定义异常 处理逻辑
    public Map<String,Object> myException(MyException my){
        //能走到这说明就有异常信息
        //如果有多个异常信息就执行了MyException的public MyException(Map map)构造方法，
        // 然后把code和msg信息放进map1
        Map map1 = my.getMap();
        if(map1!=null){//有多条异常
            return map1;
        }
        //走到这说明有一条异常，就会执行 public MyException(String msg)构造方法
        Map<String,Object> map = new HashMap<>();
        map.put("code",600);
        map.put("msg",my.getMsg());
        return map;
    }
}
