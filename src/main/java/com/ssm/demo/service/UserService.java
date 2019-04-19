package com.ssm.demo.service;

import com.ssm.demo.domain.User;

import java.util.List;

/**
 * 业务实现接口
 */
public interface UserService {
    public User find(String name);
    public List<User> findUserLikeName(String name);
    public User findUserById(int id);
}
