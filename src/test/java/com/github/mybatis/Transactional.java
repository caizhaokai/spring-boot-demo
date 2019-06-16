package com.github.mybatis;

import com.ApplicationTestRoot;
import com.github.mybatis.service.TransactionalService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 事务测试
 */
public class Transactional extends ApplicationTestRoot {
    @Autowired
    private TransactionalService service;

    /**
     * 测试 @Transactional 注解，正常情况
     */
    @Test
    public void test001() {
        service.testTransactional(false);
    }

    /**
     * 测试 @Transactional 注解，异常情况
     */
    @Test
    public void test002() {
        service.testTransactional(true);
    }

    /**
     * 测试 @Transactional 注解参数， timeout参数
     */
    @Test
    public void test003() {
        long start = System.currentTimeMillis();
        try {
            service.testTSTimeout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    /**
     * 测试spring 数据库事务类，正常情况
     */
    @Test
    public void test004() {
        service.testHandleCommitTS(false);
    }

    /**
     * 测试spring 数据库事务类，异常情况
     */
    @Test
    public void test005() {
        service.testHandleCommitTS(true);
    }
}
