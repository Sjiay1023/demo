package com.ssm.demo.dao;

import com.ssm.demo.domain.Student;
import com.ssm.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User实体映射器接口
 * Mapper注解是Mybatis框架使用，标记为映射器
 * Repository注解是Spring框架使用，标记为一个Bean
 */
@Mapper
@Repository
public interface UserMapper{
   public Student selectUserByName(String name);

   @Select("select * from student where name like #{name}")
   public List<Student> selectUserLikeName(String name);

   @Select("select * from student where id=#{id}")
   public Student selectUserById(int id);

   /**
    * 根据id查询
    * @param id
    * @return
    */
   public Student getById(Integer id);

   /**
    * 查询全部
    * @return
    */
   public List<Student> list();

   /**
    * 插入
    * @param student
    * @return
    */
   public int insert(Student student);

    /**
     * 根据id查询所有课程
     * @param id
     * @return
     */
   public Student selectCourseById(Integer id);

   /*
    * 登录时查询用户名和密码
    * @param userName
    * @param password
    * @return
    */
   User selectByUserNamePassword(@Param("userName") String userName,@Param("password")  String password);

}
