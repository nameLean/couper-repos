package com.example.test8_11.dao;

import com.example.test8_11.pojo.User;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int queryUsernameIsExistence(String username);

    User queryIsCanLogin(String username, String password);

    int queryUserMobileIsExistence(String userMobile);

    int queryUserEmailIsExistence(String userEmail);
}