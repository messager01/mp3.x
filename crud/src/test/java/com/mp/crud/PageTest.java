package com.mp.crud;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mp.crud.dao.UserMapper;
import com.mp.crud.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * create by Shipeixin on 2020/4/2 16:15
 */
@SpringBootTest
public class PageTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void selectPage(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",26);

        Page<User> page = new Page<>(1, 2);

        //  获得记录:[User(UserId=1087982257332887553, realName=大boss, age=40, email=boss@baomidou.com, managerId=null, createTime=2019-01-11T14:20:20, remark=null), User(UserId=1088250446457389058, realName=李艺伟, age=28, email=lyw@baomidou.com, managerId=1088248166370832385, createTime=2019-02-14T08:31:16, remark=null)]
        IPage<User> iPage = userMapper.selectPage(page, queryWrapper);

        System.out.println("总页数:"+iPage.getPages());

        System.out.println("总记录数:"+iPage.getTotal());

        System.out.println("获得记录:"+iPage.getRecords());
    }


    @Test
    public void testMapPage(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",26);
        // 加上 false   不会查 总记录数  只返回查询结果
        Page<User> page = new Page<>(1, 2,false);

        IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, queryWrapper);

        System.out.println("总页数:"+iPage.getPages());

        System.out.println("总记录数:"+iPage.getTotal());

//获得记录:[{realName=大boss, create_time=2019-01-11 14:20:20.0, user_id=1087982257332887553, age=40, email=boss@baomidou.com}, {realName=李艺伟, create_time=2019-02-14 08:31:16.0, user_id=1088250446457389058, manager_id=1088248166370832385, age=28, email=lyw@baomidou.com}]
        System.out.println("获得记录:"+iPage.getRecords());
    }



    @Test
    public void testMyPage(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",26);
        Page<User> page = new Page<>(1, 2);
        IPage<User> iPage = userMapper.selectUserPage(page, queryWrapper);

        System.out.println("总页数:"+iPage.getPages());

        System.out.println("总记录数:"+iPage.getTotal());

        System.out.println("获得记录:"+iPage.getRecords());
    }
}
