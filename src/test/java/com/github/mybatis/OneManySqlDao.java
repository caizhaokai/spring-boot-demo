package com.github.mybatis;

import com.ApplicationTestRoot;
import com.github.mybatis.domain.User2;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 测试一对一，一对多查询
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OneManySqlDao extends ApplicationTestRoot {
    @Autowired
    private com.github.mybatis.dao.OneManySqlDao dao;

    /**
     * 插入测试数据
     */
    @Test
    public void test001() {
        dao.deleteLogin("ID0001");
        dao.deleteIdentity("ID0001");
        dao.insertLogin("ID0001", "myName", "myPassword");
        dao.insertIdentity("ID0001", "01", "12345678");
        dao.insertIdentity("ID0001", "02", "1234567890");
    }

    /**
     * 测试一对一，一对多查询
     */
    @Test
    public void test002() {
        User2 user = dao.selectUser("ID0001");
        System.out.println(user);
        System.out.println(user.getLogin());
        System.out.println(user.getIdentityList());
    }
}
