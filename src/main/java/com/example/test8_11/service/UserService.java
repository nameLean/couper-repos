package com.example.test8_11.service;

import com.example.test8_11.common.SystemResult;
import com.example.test8_11.pojo.parameter.UserParam;

public interface UserService {
    SystemResult login(UserParam userParam);

    SystemResult register(UserParam userParam);


}
