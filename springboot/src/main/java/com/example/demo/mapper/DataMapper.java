package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Data;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DataMapper extends BaseMapper<Data> {
    @Select("SELECT * FROM data WHERE to_days(ts) = to_days(now())")
    public List<Data> getData();
}
