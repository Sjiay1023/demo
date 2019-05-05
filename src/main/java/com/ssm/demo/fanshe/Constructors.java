package com.ssm.demo.fanshe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/*
 * 通过Class对象可以获取某个类中的：构造方法、成员变量、成员方法；并访问成员；
 *
 * 1.获取构造方法：
 *      1).批量的方法：
 *          public Constructor[] getConstructors()：所有”公有的”构造方法
            public Constructor[] getDeclaredConstructors()：获取所有的构造方法(包括私有、受保护、默认、公有)

 *      2).获取单个的方法，并调用：
 *          public Constructor getConstructor(Class… parameterTypes):获取单个的”公有的”构造方法：
 *          public Constructor getDeclaredConstructor(Class… parameterTypes):获取”某个构造方法”可以是私有的，或受保护、默认、公有；
 *
 *          调用构造方法：
 *          Constructor–>newInstance(Object… initargs)
 */
public class Constructors {
    public static void main(String[] args)throws Exception{
        //1.加载class对象
        Class clazz = Class.forName("com.ssm.demo.fanshe.Student");

        //2.获取所有共有构造方法
        System.out.println("**********************所有公有构造方法***************************");
        Constructor[] conArray = clazz.getConstructors();
        for(Constructor c : conArray){
            System.out.println(c);
        }

        System.out.println("************所有的构造方法(包括：私有、受保护、默认、公有)*********");
        conArray = clazz.getDeclaredConstructors();
        for(Constructor c : conArray){
            System.out.println(c);
        }

        System.out.println("*****************获取公有、无参的构造方法***********************");
        Constructor con = clazz.getConstructor(null);
        //1>、因为是无参的构造方法所以类型是一个null,不写也可以：这里需要的是一个参数的类型，切记是类型
        //2>、返回的是描述这个无参构造函数的类对象。
        System.out.println("con = " + con);

        //调用构造方法
        Object obj = con.newInstance();
        System.out.println("obj = " + obj);
        Student stu = (Student)obj;

        System.out.println("******************获取私有构造方法，并调用*********************");
        con = clazz.getDeclaredConstructor(char.class);
        System.out.println(con);
        //调用构造方法
        con.setAccessible(true);
        obj = con.newInstance('男');


        //获取成员变量
        System.out.println("************获取所有公有的字段********************");
        Field[] fieldArray = clazz.getFields();
        for (Field f : fieldArray){
            System.out.println(f);
        }

        System.out.println("************获取所有的字段(包括私有、受保护、默认的)*************");
        fieldArray = clazz.getDeclaredFields();
        for (Field f : fieldArray){
            System.out.println(f);
        }

        System.out.println("*************获取公有字段**并调用************************");
        Field f = clazz.getField("name");
        System.out.println(f);
        //获取一个对象
        Object object = clazz.getConstructor().newInstance();//产生Student对象
        //为字段设置值
        f.set(obj,"刘德华");
        Student student = (Student)obj;
        System.out.println("验证姓名：" + student.name);

        System.out.println("**************获取私有字段****并调用********************************");
        f = clazz.getDeclaredField("phoneNum");
        System.out.println(f);
        f.setAccessible(true);//暴力反射，解除私有限定
        f.set(obj, "18888889999");
        System.out.println("验证电话：" + student);



    }
}
