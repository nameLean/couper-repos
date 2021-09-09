package com.example.test8_11.service.impl;

import com.example.test8_11.common.SystemEnum;
import com.example.test8_11.common.SystemResult;
import com.example.test8_11.dao.UserDao;
import com.example.test8_11.pojo.User;
import com.example.test8_11.pojo.parameter.UserParam;
import com.example.test8_11.pojo.vo.UserVo;
import com.example.test8_11.service.UserService;
import com.example.test8_11.util.FileUtil;
import com.example.test8_11.util.JwtUtil;
import com.example.test8_11.util.MailService;
import com.example.test8_11.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private MailService mailService;
//    @Autowired
//    private SystemResult systemResult;

    @Override
    public SystemResult login(UserParam userParam) {
        //能走到这里说明传过来的参数已经经过校验，可以使用
        //得到用户名和密码需要到数据库查询是否存在该用户，并返回结果给control层
        User user = userDao.queryIsCanLogin(userParam.getUsername(), userParam.getPassword());
        if(user==null)return SystemResult.fail(SystemEnum.LOGIN_FAIL);//登录失败
        UserVo userVo = UserVo.generate(user);
        //如果登录成功就把数据装
        return SystemResult.success(userVo);
    }



    @Override
    public SystemResult register(UserParam userParam) {
        //controller层加了参数校验的注解，走到这证明参数校验通过，那么需要校验账户等信息是否重复
        //需要校验是否重复：用户名、电话、邮箱
        String username=userParam.getUsername();
        SystemResult usernameResult=checkUsername(username);
        if(!usernameResult.isSuccess())return usernameResult;
        //
        String userMobile = userParam.getUserMobile();
        SystemResult userMobileResult=checkUserMobile(userMobile);
        if(!userMobileResult.isSuccess())return userMobileResult;
        //
        String userEmail = userParam.getUserEmail();
        SystemResult userEmailResult=checkUserEmail(userEmail);
        if(!userEmailResult.isSuccess())return userEmailResult;

        //账户邮箱电话验证可用以后，这里就把用户传的头像等数据封装进user对象进行数据库的数据保存
        //Date date = new Date();注册时间，数据库没做这个字段，这里就不写了
        long id = snowflakeIdWorker.nextId();
        String upload = fileUtil.upload(userParam.getHeadImag());
        //这里调用构建者模式的方法把经过校验的参数对象转成能直跟数据库形成序列化转化的user对象，然后插入数据库进行注册
        User user = User.generateUserByUSerParam(userParam, upload);
        userDao.insertSelective(user);//todo 这里是什么插入的方法？

        //下面逻辑是用户名等信息每人使用现在可以注册，那么就发送邮箱验证激活账号，也是确保密码丢失以后能找回密码的邮箱确认
        //这里是生成jwt验证的中间那段的信息以生成jwt
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("username",userParam.getUsername());
        //发送邮件，发送jwt，让用户点击走jwt验证的接口
        mailService.sendHtmlMail("全珍珠宝——用户登录的邮件验证",
                "<a href='http://localhost:8080/email_activation?jwt="+ JwtUtil.sign(objectObjectHashMap)+"></a>"
        , new String[]{userParam.getUserEmail()});
        return SystemResult.success("注册成功，请在15分钟内登录邮箱激活账号");
    }

    private SystemResult checkUserEmail(String userEmail) {
        int userEmailIsExistence=userDao.queryUserEmailIsExistence(userEmail);
        if(userEmailIsExistence>0)return SystemResult.fail(SystemEnum.REGISTER_EMAIL_FAIL);
        return SystemResult.success();
    }

    private SystemResult checkUserMobile(String userMobile) {
        int userMobileIsExistence=userDao.queryUserMobileIsExistence(userMobile);
        if(userMobileIsExistence>0)return SystemResult.fail(SystemEnum.REGISTER_MOBIL_FAIL);
        return SystemResult.success();
    }

    private SystemResult checkUsername(String username) {
        int usernameIsExistence=userDao.queryUsernameIsExistence(username);
        if(usernameIsExistence>0)return SystemResult.fail(SystemEnum.REGISTER_USERNAME_FAIL);
        return SystemResult.success();
    }
}
