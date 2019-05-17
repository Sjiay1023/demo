package com.ssm.demo.dao;

import com.ssm.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
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
   public User selectUserByName(String name);

   @Select("select * from user where name like #{name}")
   public List<User> selectUserLikeName(String name);

   @Select("select * from user where id=#{ids}")
   public User selectUserById(int id);

   /**
    * 根据id查询
    * @param id
    * @return
    */
   public User getById(Integer id);

   /**
    * 查询全部
    * @return
    */
   public List<User> list();

   /**
    * 插入
    * @param user
    * @return
    */
   public int insert(User user);

    /**
     * 根据id查询所有课程
     * @param id
     * @return
     */
   public User selectCourseById(Integer id);
}
