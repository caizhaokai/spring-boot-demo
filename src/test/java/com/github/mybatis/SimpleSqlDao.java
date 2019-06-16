package com.github.mybatis;

import com.ApplicationTestRoot;
import com.github.mybatis.domain.User;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试简单sql语句
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleSqlDao extends ApplicationTestRoot {
    @Autowired
    private com.github.mybatis.dao.SimpleSqlDao dao;

    /**
     * 测试连接
     */
    @Test
    public void test001() {
        System.out.println(dao.testSqlConnent());
    }

    /**
     * 测试新增 bean
     */
    @Test
    public void test002() {
        User user = new User("10001", "helloworld", 11);
        System.out.println("success insert user by bean: " + dao.insertUser(user));
        System.out.println(dao.selectUserById("10001"));
    }

    /**
     * 测试新增 map
     */
    @Test
    public void test003() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "10002");
        map.put("name", "helloworld2");
        map.put("age", 12);
        System.out.println("success insert user by map: " + dao.insertUserByMap(map));
        System.out.println(dao.selectUserById("10002"));
    }

    /**
     * 测试新增 param
     */
    @Test
    public void test004() {
        int n = dao.insertUserByParam("10003", "helloworld3", 13);
        System.out.println("success insert user by param: " + n);
        System.out.println(dao.selectUserById("10003"));
    }

    /**
     * 测试修改
     */
    @Test
    public void test005() {
        User user = new User("10003", "updateName", 23);
        System.out.println(dao.updateUser(user));
        System.out.println(dao.selectUserById("10003"));
    }

    /**
     * 测试删除
     */
    @Test
    public void test006() {
        System.out.println(dao.deleteUserById("10003"));
        System.out.println(dao.selectUserById("10003"));
    }

    /**
     * 测试查询 bean
     */
    @Test
    public void test007() {
        User user = dao.selectUserById("10001");
        System.out.println(user);
    }

    /**
     * 测试查询 list
     */
    @Test
    public void test008() {
        List<User> list = dao.selectUser();
        for (User bean : list) {
            System.out.println(bean);
        }
    }

    /**
     * 测试 truncate
     */
    @Test
    public void test009() {
        dao.truncateUser();
        System.out.println("user table num is : " + dao.selectCountUser());
    }
}
