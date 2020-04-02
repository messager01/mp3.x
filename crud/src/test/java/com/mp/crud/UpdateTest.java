package com.mp.crud;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mp.crud.dao.UserMapper;
import com.mp.crud.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * create by Shipeixin on 2020/4/2 16:29
 */
@SpringBootTest
public class UpdateTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void updateById(){
        User user = new User();
        user.setUserId(1088248166370832385L);
        user.setAge(26);
        user.setEmail("wtf@baomidou2.com");
//UPDATE mp_user SET age=?, email=? WHERE user_id=?
        int rows = userMapper.updateById(user);
        System.out.println("影响记录数："+rows);
    }


    @Test
    public void updateByWrapper(){
        // Preparing: UPDATE mp_user SET age=? WHERE name = ? AND age = ?
        // Parameters: 29(Integer), 李艺伟(String), 28(Integer)
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name","李艺伟").eq("age",28);
        User user = new User();
        user.setAge(29);
        int rows = userMapper.update(user, updateWrapper);
        System.out.println("影响记录数："+rows);
    }


    @Test
    public void updateWrapper2(){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
                                                         //  对少量字段更新时，使用set直接设置更新字段
        updateWrapper.eq("name","李艺伟").eq("age",29).set("age",30);

        int rows = userMapper.update(null, updateWrapper);
        System.out.println("影响记录数："+rows);
    }

    @Test    //  lambda 更新  防误写
    public void updateWrapperLambda(){
        LambdaUpdateWrapper<User> lambdaUpdate = Wrappers.<User>lambdaUpdate();
        lambdaUpdate.eq(User::getRealName,"李艺伟").eq(User::getAge,30).set(User::getAge,31);

        int rows = userMapper.update(null, lambdaUpdate);
        System.out.println("影响记录数："+rows);
    }
}
