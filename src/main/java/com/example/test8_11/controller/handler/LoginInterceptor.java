package com.example.test8_11.controller.handler;


import com.example.test8_11.common.SystemConstant;
import com.example.test8_11.common.SystemEnum;
import com.example.test8_11.common.exception.MyException;
import com.example.test8_11.pojo.vo.UserVo;
import com.example.test8_11.util.CookieUtil;
import com.example.test8_11.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtils redisUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handler1 = (HandlerMethod) handler;
            Method method = handler1.getMethod();
            Cookie cookie = CookieUtil.getCookie(SystemConstant.User.COOKIE_LOGIN_KEY, request);

            if(method.isAnnotationPresent(NeedLogin.class)) {
                if(cookie==null) throw new MyException(SystemEnum.NO_LOGIN);

                String token = cookie.getValue();
                String redisKey = SystemConstant.Redis.REDIS_LOGIN_PREFIX + token;

                UserVo userVo = redisUtils.hmgetObject(redisKey, UserVo.class);
                if(userVo==null){//说明可能没有登录 还有可能登录过期
                    if(redisUtils.ttl(redisKey)==-2){
                        throw new MyException(SystemEnum.LOGIN_EXPIRE);
                    }
                    throw new MyException(SystemEnum.NO_LOGIN);
                }
                //说明登录成功 重置过期时间
                redisUtils.expire(redisKey,SystemConstant.Redis.LOGIN_KEY_EXPIRE);
            }else {
                if (cookie != null) {
                    String token = cookie.getValue();
                    String redisKey = SystemConstant.Redis.REDIS_LOGIN_PREFIX + token;
                    //说明登录成功 重置过期时间
                    redisUtils.expire(redisKey, SystemConstant.Redis.LOGIN_KEY_EXPIRE);
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
