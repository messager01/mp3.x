package com.mp.crud;

import com.mp.crud.dao.UserMapper;
import com.mp.crud.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class CrudApplicationTests {


    @Autowired
    UserMapper userMapper;

    @Test
    void insertTest() {

        User user = new User();
        user.setRealName("向后");
        user.setAge(25);
        user.setManagerId(1088248166370832385L);
        user.setEmail("xn@baomidou.com");
        user.setCreateTime(LocalDateTime.now());
        user.setRemark("该信息不做任何代表");
        int rows = userMapper.insert(user);
        System.out.println("影响记录数为："+ rows);
    }

}
