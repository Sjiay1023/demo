package com.ssm.demo.service;

import com.ssm.demo.domain.Student;
import com.ssm.demo.domain.User;

import java.util.List;

/**
 * 业务实现接口
 */
public interface UserService {
    public Student find(String name);
    public List<Student> findUserLikeName(String name);
    public Student findUserById(int id);
    public Student getById(Integer id);
    public List<Student> list();
    public void insert(Student student);
    public Student selectCourseById(Integer id);

    public User selectByUserNamePassword(String userName,String password);
}
