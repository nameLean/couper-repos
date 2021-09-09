package com.example.test8_11.common;

public interface SystemConstant {
    //登录失败的返回值
    int LOGIN_FAIL=700;
    int NO_LOGIN=705;

    int REGISTER_USERNAME_FAIL=600;//注册——用户名失败
    int REGISTER_MOBIL_FAIL=601;//注册——电话失败
    int REGISTER_EMAIL_FAIL=602;//注册——邮箱失败

    int SYSTEM_ERROR=702;
    int SYSTEM_SUCCESS=200;
    int NO_PERMISSION=706;
    int REPEAT_LOGIN=707;
    int WEIXIN_NOBIND=708;

    interface  Project{
        String PROJECT_PREFIX="PROJECT_";
    }
    interface  User{
        String SESSION_LOGIN_KEY="SESSION_LOGIN_KEY";

        String COOKIE_LOGIN_KEY="COOKIE_LOGIN_KEY";
    }
    interface  Redis{
        String REDIS_LOGIN_PREFIX="LOGIN_";
        int LOGIN_KEY_EXPIRE=60*15;
    }
    interface Websocket{
        String USERID="userId";
    }

    interface Category{
        String CATEGORY_PREFIX="CATEGORY_";

        String CATEGORY_CHILD_PREFIX="CATEGORY_CHILD_";
        String CATEGORY_ALL_CHILD_PREFIX="CATEGORY_ALL_CHILD_";

        String CATEGORY_ALL_PREFIX="CATEGORY_ALL";

        String CATEGORY_FIRST_LEVEL="CATEGORY_FIRST_LEVEL";
    }
    interface Product{
        String PRODUCT_PREFIX="PRODUCT_";


    }
    interface  Freemarker{
        String PRODUCT_HTML_PATH="product.ftl";
    }
}
