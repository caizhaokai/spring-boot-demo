package com.github.mybatis;

import com.ApplicationTestRoot;
import com.github.mybatis.dao.SimpleSqlDao;
import com.github.mybatis.domain.User;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试动态sql
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DynamicSqlDao extends ApplicationTestRoot {

    @Autowired
    private com.github.mybatis.dao.DynamicSqlDao dao;

    @Autowired
    private SimpleSqlDao sdao;

    /**
     * 测试使用 foreach 遍历 list ，进行批量新增
     */
    @Test
    public void test001() {
        List<User> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            User user = new User();
            user.setId("ID" + String.format("%04d", i));
            user.setName("name_" + i);
            user.setAge(i % 100);
            list.add(user);
        }
        System.out.println(dao.insertUserListWithForeach(list));
        System.out.println(sdao.selectCountUser());
    }

    /**
     * 测试使用 foreach 遍历 list ，in (list)
     */
    @Test
    public void test002() {
        List<String> nameList = new ArrayList<>();
        nameList.add("name_1");
        nameList.add("name_2");
        nameList.add("name_3");
        List<User> userList = dao.selectUserByINName(nameList);
        System.out.println("list size is : " + userList.size());
        for (User bean : userList) {
            System.out.println(bean);
        }
    }

    /**
     * 测试 if
     */
    @Test
    public void test003() {
        User user = new User("ID0002", "name_2", 11);
        List<User> list = dao.selectUserWithIf(user);
        for (User bean : list) {
            System.out.println(bean);
        }
        System.out.println("=========================");
        user = new User("", "name_2", 11);
        list = dao.selectUserWithIf(user);
        for (User bean : list) {
            System.out.println(bean);
        }
    }

    /**
     * 测试 choose
     */
    @Test
    public void test004() {
        User user = new User("ID0002", "name_2", 11);
        List<User> list = dao.selectUserWithChoose(user);
        for (User bean : list) {
            System.out.println(bean);
        }
        System.out.println("=========================");
        user = new User("", "name_2", 11);
        list = dao.selectUserWithChoose(user);
        for (User bean : list) {
            System.out.println(bean);
        }
    }

    /**
     * 测试 set
     */
    @Test
    public void test005() {
        User user = new User("ID0002", null, 22);
        System.out.println(sdao.selectUserById("ID0002"));
        System.out.println(dao.updateUserWithSet(user));
        System.out.println(sdao.selectUserById("ID0002"));
    }

    /**
     * 测试 bind
     */
    @Test
    public void test006() {
        List<User> list = dao.selectUserWithBind("name_2");
        for (User bean : list) {
            System.out.println(bean);
        }
    }
}
