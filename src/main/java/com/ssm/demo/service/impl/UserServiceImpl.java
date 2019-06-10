package com.ssm.demo.service.impl;

import com.ssm.demo.dao.UserMapper;
import com.ssm.demo.domain.Student;
import com.ssm.demo.domain.User;
import com.ssm.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserMapper userMapper;

    @Override
    public Student find(String name) {
        return userMapper.selectUserByName(name);
    }

    @Override
    public List<Student> findUserLikeName(String name) {
        return userMapper.selectUserLikeName("%"+name+"%");
    }

    @Override
    public Student findUserById(int id) {

        return userMapper.selectUserById(id);
    }

    @Override
    public Student getById(Integer id) {
        return userMapper.getById(id);
    }

    @Override
    public List<Student> list() {
        return userMapper.list();
    }

    @Override
    @Transactional
    public void insert(Student student) {
        userMapper.insert(student);
//        if(1==1){
//            throw new NullPointerException();
//        }
    }

    @Override
    public Student selectCourseById(Integer id) {
        return userMapper.selectCourseById(id);
    }

    @Override
    public User selectByUserNamePassword(String userName, String password) {
        return userMapper.selectByUserNamePassword(userName,password);
    }
}
