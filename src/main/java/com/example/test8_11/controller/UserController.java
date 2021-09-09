package com.example.test8_11.controller;

import com.example.test8_11.common.SystemConstant;
import com.example.test8_11.common.SystemResult;
import com.example.test8_11.controller.handler.NeedLogin;
import com.example.test8_11.pojo.parameter.UserParam;
import com.example.test8_11.pojo.vo.UserVo;
import com.example.test8_11.service.UserService;
import com.example.test8_11.util.*;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@Slf4j
@Api(tags = "用户模块" ,description = "接口api")//描述接口的swagger
public class UserController {
    @Autowired//这个注解相当于是从IOC容器的单例线程池中取出来这个对象
    private UserService userService;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    @Autowired
    private RedisUtils redisUtils;


    @PostMapping("register")//注册并插进数据库
    public SystemResult register(@Validated UserParam userParam,BindingResult bindingResult){
        log.info("进行用户注册的参数校验：{}", userParam);
        BindingResultUtil.check(bindingResult);
        return userService.register(userParam);
    }
    @GetMapping("email_activation")//邮箱激活账号
    public SystemResult email_activation(String jwt){
        //进行比对盐是不是系统发送的jwt，从而取出jwt里面的用户信息确认是哪个用户点击的注册请求
        Map<String, String> verify = JwtUtil.verify(jwt);

        //TODO 怎么拿到username？  拿到以后更改数据库用户账号状态，update user2 set status=0 where username=#{username}
        //校验
        return SystemResult.success();
    }


    @ApiOperation(value = "用户登录", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, paramType = "path", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "path", dataType = "String")
    })
    @ApiResponses({

            @ApiResponse(code = 700, message = "用户名或密码数错误"),
            @ApiResponse(code = 200, message = "请求成功"),


    })
    //这里@Validated注解不能放到HttpServletRequest request前面，会捕获到request里面的参数从而报出系统异常
    @PostMapping("/login")
    public SystemResult login(HttpServletResponse response, @Validated UserParam userParam, BindingResult bindingResult){
        log.info("进行用户名和密码的登录参数校验：{}", userParam);
        BindingResultUtil.check(bindingResult);
        //能从上面走到这儿说明参数校验成功没有异常，这里可以返回给前端code和成功的msg，也可以直接写其他业务代码
        SystemResult<UserVo> loginResult = userService.login(userParam);//这里不加泛型行就会报转换异常
        if(loginResult.isSuccess()){//登录成功就把token凭证放入cookie，让用户访问其他微服务时可以不用登录就能实现单点登录功
            //request.getSession().setAttribute(SystemConstant.User.SESSION_LOGIN_KEY,loginResult.getData());
            String token = String.valueOf(snowflakeIdWorker.nextId());
            //这里调用静态工具类生成cookie并在cookie工具类里利用response对象把token放进去了
            CookieUtil.addCookie(SystemConstant.User.COOKIE_LOGIN_KEY,token,response);
            //token凭证放进cookie相当于把凭证交给了用户端浏览器，那么服务端也需要知道这个token，这里用redis做
            //然后把固定前缀和token作为key ,经过数据库查询出的data作为value存入redis
            String redisKey=SystemConstant.Redis.REDIS_LOGIN_PREFIX+token;
            UserVo data = loginResult.getData();
            redisUtils.hmset(redisKey,data);
            //设置redis的过期时间，登录过期
            redisUtils.expire(redisKey,SystemConstant.Redis.LOGIN_KEY_EXPIRE);
            //包含抽象方法的类称为抽象类,抽象类不能用来创建对象,抽象类是对一种事物的抽象，即对类抽象，
            // 而接口是对行为的抽象。抽象类是对整个类整体进行抽象，包括属性、行为，但是接口却是对类局部（行为）进行抽象。
        }
        return loginResult;
    }

    //拦截器校验是否登录，登录后该方法模拟实现访问其他资源
    @GetMapping("/loginAfterTest")
    @NeedLogin
    public SystemResult loginAfterTest(HttpServletRequest request){
        //能运行下面的代码说明已经登陆了，那么就要取出cookie中的token(放的时候有一个固定的前缀)，
        // 组装出redis的key,然后取出value(也就是里面存了userVo的对象)
        Cookie cookie = CookieUtil.getCookie(SystemConstant.User.COOKIE_LOGIN_KEY, request);//得到cookie
        String token = cookie.getValue();
        String redisKey=SystemConstant.Redis.REDIS_LOGIN_PREFIX+token;
        UserVo userVo = redisUtils.hmgetObject(redisKey, UserVo.class);
        return SystemResult.success(userVo);


    }



}
