package com.ssm.demo.service.impl;

import com.ssm.demo.dao.UserMapper;
import com.ssm.demo.domain.User;
import com.ssm.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserMapper userMapper;

    @Override
    public User find(String name) {
        return userMapper.selectUserByName(name);
    }

    @Override
    public List<User> findUserLikeName(String name) {
        return userMapper.selectUserLikeName("%"+name+"%");
    }

    @Override
    public User findUserById(int id) {

        return userMapper.selectUserById(id);
    }
}
