package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("data")
@lombok.Data
public class Data {
    @TableId(type = IdType.AUTO)
    private Integer id;       //di
    private String temperature;  //温度
    private String humidity;     //湿度
    private String ts;           //时间戳
    private String place;        //地点
}
