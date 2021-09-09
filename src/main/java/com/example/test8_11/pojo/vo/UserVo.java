package com.example.test8_11.pojo.vo;

import com.example.test8_11.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

//全称为：Data Transfer Object，即数据传输对象。一般用于向数据层外围提供仅需的数据，如查询一个表有50个字段，
// 界面或服务只需要用到其中的某些字段，DTO就包装出去的对象。可用于隐藏数据层字段定义，
// 也可以提高系统性能，减少不必要字段的传输损耗
//这里的字段和数据库不一致,dto做个字段转换
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class UserVo {
    private int userId;
    private String userName;
    private String passWord;
    private MultipartFile file;

    //这里调用建造者模式，把user对象转换成dto对象以供程序使用
    public static UserVo generate(User user){
        return UserVo.builder().userId(user.getId())
                .userName(user.getUserName())
                .passWord(user.getUserPassword())
                .build();
    }

}
