package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;       //di
    private String username;  //用户名
    private String password;  //密码
    private String nickName;  //昵称
    private Integer age;      //年龄
    private String sex;       //性别
    private String address;   //地址
}
