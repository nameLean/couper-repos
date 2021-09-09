package com.example.test8_11.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JacksonUtil {
    @Autowired
    private ObjectMapper objectMapper;
    //把任意对象转成json字符串
    public String writeAsString(Object obj){
        try {
            String s = objectMapper.writeValueAsString(obj);
            return s;
        }catch (JsonProcessingException e) {
            log.error("object convert json exception,obj:{}",obj);
            throw  new RuntimeException(e);
        }
    }
    //把json字符串转换为对象 对象得指定class
    public <T>T readValue(String str,Class<T> tClass){

        try {
            return objectMapper.readValue(str, tClass);
        } catch (JsonProcessingException e) {
            log.error("str convert Object exception,str:{},class:{}",str,tClass);
            throw  new RuntimeException(e);
        }
    }
    public <T>T readValue(byte[] bytes,Class<T> tClass){

        try {
            return objectMapper.readValue(bytes, tClass);
        }  catch (IOException e) {
            log.error("str convert Object exception,str:{},class:{}",bytes,tClass);
            throw  new RuntimeException(e);
        }
    }
    //json字符串转换为集合
    public <T> List<T> readValueList(String str, Class<T> tClass){
        try {
            TypeFactory typeFactory = TypeFactory.defaultInstance();
            return objectMapper.readValue(str,typeFactory.constructParametricType(List.class,tClass));
        } catch (JsonProcessingException e) {
            log.error("str convert List<Object> exception,str:{},class:{}",str,tClass);
            throw  new RuntimeException(e);
        }
    }
    //json对象转成map
    public <K,V>Map<K,V> readValueMap(String str, Class<K> tClass, Class<V> vClass){
        try {
            TypeFactory typeFactory = TypeFactory.defaultInstance();
            return objectMapper.readValue(str,typeFactory.
                    constructParametricType(HashMap.class,tClass,vClass));
        } catch (JsonProcessingException e) {
            log.error("str convert Map<K,V> exception,str:{},tClass:{},vClass:{}",str,tClass,vClass);
            throw  new RuntimeException(e);
        }
    }
    public Map<String,String> readValueMap(String str){
        try {
            return objectMapper.readValue(str,HashMap.class);
        } catch (JsonProcessingException e) {
            log.error("str convert Map exception,str:{}",str);
            throw  new RuntimeException(e);
        }
    }
    public Map readValue(String str){
        try {
            return objectMapper.readValue(str,HashMap.class);
        } catch (JsonProcessingException e) {
            log.error("str convert Map exception,str:{}",str);
            throw  new RuntimeException(e);
        }
    }
}
