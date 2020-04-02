package com.mp.crud;

import com.mp.crud.dao.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * create by Shipeixin on 2020/4/2 16:49
 */
@SpringBootTest
public class DeleteTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void test1(){
        int rows = userMapper.deleteById(1245339425374789634L);
        System.out.println(rows);
    }

    @Test
    public void deleteByMap(){
         Map<String,Object> params = new HashMap();

         params.put("name","向前");
         params.put("age",25);
        int i = userMapper.deleteByMap(params);
        System.out.println("infect:"
        +i);

    }


    @Test
    public void test(){
         userMapper.deleteBatchIds(Arrays.asList(564899,49846556,484684565));
    }
}
