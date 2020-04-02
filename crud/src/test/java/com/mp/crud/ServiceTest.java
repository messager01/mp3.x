package com.mp.crud;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mp.crud.entity.User;
import com.mp.crud.service.UserSerivce;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * create by Shipeixin on 2020/4/2 18:02
 */
@SpringBootTest
public class ServiceTest {

    @Autowired
    private UserSerivce userSerivce;


    @Test
    public void test(){
        User one = userSerivce.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 25),false);
        System.out.println(one);
    }
}
