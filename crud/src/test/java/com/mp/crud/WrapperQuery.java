package com.mp.crud;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mp.crud.dao.UserMapper;
import com.mp.crud.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by Shipeixin on 2020/4/2 12:15
 */
@SpringBootTest
public class WrapperQuery {


    @Autowired
    UserMapper userMapper;

    /*
    *
    * 名字中包含雨并且年龄小于40
	  name like '%雨%' and age<40
*/

    @Test
    public void test1(){
        // queryWrapper 继承了 AbstractWrapper
        QueryWrapper<User>  queryWrapper1 = new QueryWrapper<>();
        //QueryWrapper<User> queryWrapper2 = Wrappers.<User>query();
        queryWrapper1.like("name","雨").lt("age",40);

        //SELECT user_id,name AS realName,age,email,manager_id,create_time FROM mp_user WHERE name LIKE ? AND age < ?
        List<User> users = userMapper.selectList(queryWrapper1);
        for (User user : users) {
            System.out.println(user);
        }
    }



    /*
    *
    * 名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
   name like '%雨%' and age between 20 and 40 and email is not null

    * */
    @Test
    public void test2(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("name","雨")
                .ge("age",20)
                .le("age",40)
                .isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }


    /*
    *
    *
    * 名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
    * name like '王%' or age>=25 order by age desc,id asc
    * */
    @Test
    public void test3(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","王")
                .or().ge("age",25)
                .orderByDesc("age").orderByAsc("user_id");
//SELECT user_id,name AS realName,age,email,manager_id,create_time FROM mp_user WHERE name LIKE ? OR age >= ? ORDER BY age DESC , user_id ASC
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }


    /**
     * 创建日期为2019年2月14日并且直属上级为名字为王姓
     *       date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
     */
    @Test
    public void test4(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}","2019-02-14")
                .inSql("manager_id","select user_id from mp_user where name like '王%'");
/*
*    此方法不会存在sql注入的风险 建议使用  使用 一个参数的是直接拼接数据，存在SQL注入风险
* SELECT user_id,name AS realName,age,email,manager_id,create_time FROM mp_user
 WHERE date_format(create_time,'%Y-%m-%d')=?
 AND manager_id IN (select user_id from mp_user where name like '王%')
*
* */
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }


    /**
     * 名字为王姓并且（年龄小于40或邮箱不为空）
     *     name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void test5(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name","王")
                .and(wq->wq.lt("age",40).or().isNotNull("email"));
   // SELECT user_id,name AS realName,age,email,manager_id,create_time FROM mp_user WHERE name LIKE ? AND ( age < ? OR email IS NOT NULL )
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
    }
/*
*
* 名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
    name like '王%' or (age<40 and age>20 and email is not null)
*
* */
    @Test
    public void test6(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper
                .likeRight("name","王")
                .or(wq->wq.between("age",20,40).isNotNull("email"));

        /*
        *
        * SELECT user_id,name AS realName,age,email,manager_id,create_time FROM mp_user WHERE name LIKE ? OR ( age BETWEEN ? AND ? AND email IS NOT NULL )
         *
        * */
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * （年龄小于40或邮箱不为空）并且名字为王姓
     *     (age<40 or email is not null) and name like '王%'
     */
    @Test
    public void test7(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
//    正常嵌套 不带 AND 或者 OR
//    例: nested(i -> i.eq("name", "李白").ne("status", "活着"))--->(name = '李白' and status <> '活着')
        queryWrapper.nested(wq->wq.lt("age",40).or().isNotNull("email"))
                .likeRight("name","王");

     //  Preparing: SELECT user_id,name AS realName,age,email,manager_id,create_time FROM mp_user WHERE ( age < ? OR email IS NOT NULL ) AND name LIKE ?
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /*
    *
    * 年龄为30、31、34、35
    age in (30、31、34、35)

    * */
    @Test
    public void test8(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(30,31,34,35));
  //  SELECT user_id,name AS realName,age,email,manager_id,create_time FROM mp_user WHERE age IN (?,?,?,?)
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }


    /**
     * 9、只返回满足条件的其中一条语句即可
     * limit 1
     */

    /*
    *
    * SELECT user_id,name AS realName,age,email,manager_id,create_time FROM mp_user WHERE age IN (?,?,?,?) limit 1
     *
    * */
    @Test
    public void test9(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(30,31,34,35)).last("limit 1");
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }


    /**
     * 10、名字中包含雨并且年龄小于40(需求1加强版)
     * 第一种情况：select id,name
     * 	           from user
     * 	           where name like '%雨%' and age<40
     */
    @Test
    public void test10(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id","name").like("name","雨").lt("age",40);
        // 只查询部分字段
        // SELECT user_id,name FROM mp_user WHERE name LIKE ? AND age < ?
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /*
    * 第二种情况：select id,name,age,email
	           from user
	           where name like '%雨%' and age<40
    * */
    @Test
    public void test11(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").lt("age",40)
                .select(User.class,info->!info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"));
              // com.baomidou.mybatisplus.core.metadata public class TableFieldInfo
                //extends Object
                //implements Constants
                    //数据库表字段反射信息

        //  SELECT user_id,name AS realName,age,email FROM mp_user WHERE name LIKE ? AND age < ?
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    // condition 条件查询
    @Test
    public void test12(){
        testCondition("","x");
    }


    // SELECT user_id,name AS realName,age,email,manager_id,create_time FROM mp_user WHERE email LIKE ?
    public void testCondition(String name,String email){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),"name",name)
                .like(StringUtils.isNotEmpty(email),"email",email);
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }

    }

    // 实体作为条件构造器构造方法的参数

    @Test
    public void selectByWrapperEntity(){
        User whereUser = new User();
        // QueryWrapper 中存在 有参构造器
        //  会把 entity中的条件当做参数 设置进来
        whereUser.setRealName("刘红雨");
        whereUser.setAge(32);
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>(whereUser);
        queryWrapper.like("name","雨").lt("age",40);
   //  SELECT user_id,name AS realName,age,email,manager_id,create_time FROM mp_user WHERE name=? AND age=? AND name LIKE ? AND age < ?
   /*
   *   entity 中设置的参数  是通过wrapper 设置的参数互不干扰，要小心不能重复设置参数
   *
   * */
        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }

    }


    // 测试 AllEq
    @Test
    public void testAllEq(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        Map<String,Object> params = new HashMap<>();
        params.put("name","王先风");
        params.put("age",null);
        // SELECT user_id,name AS realName,age,email,manager_id,create_time FROM mp_user WHERE name = ?
        // AllEq的重载方法，当设置为false 时，传入进来为null的字段则忽略不写进sql
        queryWrapper.allEq(params,false);

        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void test13(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").lt("age",40).select("user_id","name");

        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    /**
     * 11、按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。
     * 并且只取年龄总和小于500的组。
     * select avg(age) avg_age,min(age) min_age,max(age) max_age
     * from user
     * group by manager_id
     * having sum(age) <500
     */

    @Test
    public void testMap(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
      // SELECT avg(age) avg_age,min(age) min_age,max(age) max_age FROM mp_user GROUP BY manager_id HAVING sum(age)<?
        queryWrapper.select("avg(age) avg_age","min(age) min_age","max(age) max_age").groupBy("manager_id").having("sum(age)<{0}",500);
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        for (Map map : maps) {
            System.out.println(map);
        }
    }


    @Test
    public void testObjs(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").lt("age",40);

        // 只返回一列
        /*
        * 根据 Wrapper 条件，查询全部记录
         * <p>注意： 只返回第一个字段的值</p>
        * */

        List<Object> objects = userMapper.selectObjs(queryWrapper);
        objects.forEach(System.out::println);
    }


    @Test
    public void testCount(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").lt("age",40);
//   SELECT COUNT( 1 ) FROM mp_user WHERE name LIKE ? AND age < ?
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println("总记录数："+count);
    }


    @Test
    public void testSelectOne(){
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        /*
        *  selectOne 最多返回一条结果，当一个以上时报错，  一条或空 则 正常返回
        * */
        queryWrapper.like("name","雨").eq("age",32);

        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    /**
     *
     *    lambda 条件构造器
     *
     */
    @Test
    public void testLambda(){
       // LambdaQueryWrapper<User> lambda1 = new QueryWrapper<User>().lambda();

       // LambdaQueryWrapper<User> lambda2 = new LambdaQueryWrapper<>();

        LambdaQueryWrapper<User> lambda3 = Wrappers.<User>lambdaQuery();

        /**
         * lambda 条件构造器可以防止误写数据库字段名   lambda 表达式是调用属性的get方法，因此不会误写
         */
        lambda3.like(User::getRealName,"雨").lt(User::getAge,40);
          //  where name like '%雨%'

        List<User> users = userMapper.selectList(lambda3);
        users.forEach(System.out::println);
    }


    /**
     * 5、名字为王姓并且（年龄小于40或邮箱不为空）
     *     name like '王%' and (age<40 or email is not null)
     */
    @Test
    public void testLambda2(){
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.likeRight(User::getRealName,"王")
                .and(lqw->lqw.lt(User::getAge,40).or().isNotNull(User::getEmail));
        List<User> users = userMapper.selectList(lambdaQuery);
        users.forEach(System.out::println);
    }


    @Test
    public void testMy(){
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.likeRight(User::getRealName,"王");

        List<User> users = userMapper.selectAll(lambdaQuery);
        users.forEach(System.out::println);
    }
}
