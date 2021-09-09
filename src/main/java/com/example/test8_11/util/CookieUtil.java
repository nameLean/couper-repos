package com.example.test8_11.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void addCookie(String key, String value, HttpServletResponse response){
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);//防止xss攻击
        cookie.setMaxAge(60*60*24*7);
        cookie.setPath("/");// /user  /product
        //域名  设置当前cookie在什么域名下才能得到
        //junge.com 2级域名   www.junge.com 3级域名  所有的三级都能取到二级
        //baidu.com  fanyi.baidu.com www.baidu.com
        cookie.setDomain("junge.com");
        response.addCookie(cookie);
    }

    public static Cookie getCookie(String key, HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        if(cookies==null||cookies.length==0)return null;
        for (Cookie cookie : cookies) {
            if(key.equalsIgnoreCase(cookie.getName())){
                return cookie;
            }
        }
        return null;
    }
}
