package com.github.mybatis;

import com.ApplicationTestRoot;
import com.github.mybatis.domain.User;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试sql语句构建器
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleProviderDao extends ApplicationTestRoot {

    @Autowired
    private com.github.mybatis.providerDao.SimpleProviderDao dao;

    /**
     * 测试查询
     */
    @Test
    public void test001() {
        User user = dao.selectUserById("ID0001");
        System.out.println(user);
    }

    /**
     * 测试条件查询
     */
    @Test
    public void test002() {
        List<User> list = dao.selectUser("name_2");
        for (User user : list) {
            System.out.println(user);
        }
        System.out.println("=====================");
        list = dao.selectUser("");
        for (User user : list) {
            System.out.println(user);
        }
    }

    /**
     * 测试新增
     */
    @Test
    public void test003() {
        User user = new User("10005", "provider insert name", 11);
        System.out.println(dao.insertUser(user));
        System.out.println(dao.selectUserById("10005"));
    }

    /**
     * 测试批量新增
     */
    @Test
    public void test004() {
        List<User> list = new ArrayList<>();
        list.add(new User("10006", "name_6", 16));
        list.add(new User("10007", "name_7", 17));
        list.add(new User("10008", "name_8", 18));
        System.out.println(dao.insertUserList(list));
        System.out.println(dao.selectUserById("10006"));
        System.out.println(dao.selectUserById("10007"));
        System.out.println(dao.selectUserById("10008"));
    }

    /**
     * 测试修改
     */
    @Test
    public void test005() {
        User user = new User("10005", "updateName", 11);
        System.out.println(dao.selectUserById("10005"));
        System.out.println(dao.updateUser(user));
        System.out.println(dao.selectUserById("10005"));
    }

    /**
     * 测试删除
     */
    @Test
    public void test006() {
        System.out.println(dao.deleteUser("10005"));
        System.out.println(dao.deleteUser("10006"));
        System.out.println(dao.deleteUser("10007"));
        System.out.println(dao.deleteUser("10008"));
        System.out.println(dao.selectUserById("10005"));
    }
}
