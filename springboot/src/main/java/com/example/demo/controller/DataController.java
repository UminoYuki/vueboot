package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Data;
import com.example.demo.entity.User;
import com.example.demo.mapper.DataMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/alldata")
public class DataController {

    @Resource
    DataMapper dataMapper;

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageIndex,
                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                  @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Data> wrapper = Wrappers.<Data>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Data::getPlace, search);
        }
        Page<Data> dataPage = (Page<Data>) dataMapper.selectPage(new Page<>(pageIndex, pageSize), wrapper);
        return Result.success(dataPage);
    }



}
