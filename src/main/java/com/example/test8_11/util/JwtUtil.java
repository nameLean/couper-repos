package com.example.test8_11.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.test8_11.common.SystemEnum;
import com.example.test8_11.common.exception.MyException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtUtil {
    //当前jwtUtil没有交给spring管理  jacksonutil交给spring管理 不能之间注入
    private static final JacksonUtil jacksonUtil = MyBeanUtil.getBean(JacksonUtil.class);
    //过期时间
    private static final long EXPIRE_TIME = 15 * 60 * 1000;
    //私钥,这里也是加盐的地方
    private static final String TOKEN_SECRET = "java2104";
    //{username:zhangsan,password:lisi}
    public static String sign(Object object){
        //把对象转成map
        String str = jacksonUtil.writeAsString(object);
        Map<String, String> map = jacksonUtil.readValueMap(str);
        return sign(map);
    }
    /**
     * 生成签名，15分钟过期
     * @param **username**
     * @param **password**
     * @return map:中间那一段自定义 username:zhangsan
     */
    public static String sign(Map<String,String> map) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");
            JWTCreator.Builder builder = JWT.create();
            map.entrySet().stream().forEach(
                    //生成中间这一段
                    entry->builder.withClaim(entry.getKey(),entry.getValue())
            );

            //这里去遍历
            String sign = builder
                    .withHeader(header)//头部那一段
                    .withExpiresAt(date)//过期时间 也在中间
                    .sign(algorithm);

            // 返回token字符串
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
           throw new RuntimeException(e);
        }
    }

    /**
     * 检验token是否正确
     * @param **token**
     * @return
     */
    public static  Map<String, String> verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);//抛出过期异常
            Map<String, Claim> claims = jwt.getClaims();//取出中间这一段
            Map<String, String> collect = claims.entrySet().stream().collect(
                    Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().asString())
            );

            return collect;
        } catch (Exception e){
            if(e instanceof TokenExpiredException){//jwt过期啦
                throw new MyException(SystemEnum.JWT_EXPIRE);
            }
            //jwt不是我发的
           throw new RuntimeException(e);
        }
    }

//    public static void main(String[] args) {
//
//        byte[] encode = Base64Utils.decode("eyJhbGciOiJIUzI1NiIsIlR5cGUiOiJKd3QiLCJ0eXAiOiJKV1QifQ".getBytes());
//        System.out.println(new String(encode));
//    }
}


