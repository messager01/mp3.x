package com.mp.crud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mp.crud.dao.UserMapper;
import com.mp.crud.entity.User;
import com.mp.crud.service.UserSerivce;
import org.springframework.stereotype.Service;

/**
 * create by Shipeixin on 2020/4/2 18:00
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserSerivce {
}
