package com.mp.crud;

import com.mp.crud.dao.UserMapper;
import com.mp.crud.entity.User;
import javafx.scene.input.InputMethodTextRun;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by Shipeixin on 2020/4/1 21:19
 */
@SpringBootTest
public class QueryTest {


    @Autowired
    UserMapper userMapper;

    // 根据单个id查询
    @Test
    public void test(){
        User user = userMapper.selectById(1094590409767661570L);
        System.out.println(user);
    }


    //  传入多个id查询
    @Test
    public void selectIds(){
   //  SELECT user_id,name AS realName,age,email,manager_id,create_time FROM mp_user WHERE user_id IN ( ? , ? , ? )
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1088248166370832385L, 1088250446457389058L, 1094590409767661570L));
        for (User  user :users){
            System.out.println(user);
        }
    }


    //  用map 传多个条件查询   类似于2.x    map 的key值为数据库中的字段值

    @Test
    public void selectByMap(){
         Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("name","王天风");
        columnMap.put("age",25);

        List<User> users = userMapper.selectByMap(columnMap);
        System.out.println(users);
    }
}
