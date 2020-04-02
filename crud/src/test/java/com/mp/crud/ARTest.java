package com.mp.crud;

import com.mp.crud.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * create by Shipeixin on 2020/4/2 17:01
 */
@SpringBootTest
public class ARTest {

    @Test
    public void test(){
        User user = new User();
        user.setRealName("刘华1111");
        user.setAge(29);
        user.setEmail("what the fuck");
        user.setCreateTime(LocalDateTime.now());

        boolean insert = user.insert();
        System.out.println("是否插入成功:"+insert);
    }

    @Test
    public void selectByID(){
        User user = new User();
        User user1 = user.selectById(1245638480822833153L);
        System.out.println(user1);
    }

    @Test
    public void ARUpdate(){
        User user = new User();
        user.setUserId(1245638480822833153L);
        user.setRealName("刘华华华");
        user.updateById();
    }

    @Test
    public void ARDelete(){
        User user = new User();
        user.setUserId(1245638480822833153L);
        boolean b = user.deleteById();
        System.err.println("删除是否成功:"+b);
    }

    //  user.insertOrUpdate   如果存在设置了id 就是更新  没有设置id就是插入
}
