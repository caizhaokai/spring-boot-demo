package com.github.redis;


import com.ApplicationTestRoot;
import com.github.mybatis.domain.User;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;
import java.util.Set;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisServiceCache extends ApplicationTestRoot {
    @Autowired
    private RedisCacheUserService service;

    @Autowired
    private ValueOperations<String, Object> redisString;

    /**
     * 测试 @Cacheable 注解，缓存bean
     */
    @Test
    public void test001() {
        System.out.println("redis before use : " + redisString.get("user::ID0001"));
        System.out.println(service.selectUserById("ID0001"));
        System.out.println("redis after use : " + redisString.get("user::ID0001"));
        System.out.println();

        System.out.println("redis before use : " + redisString.get("user::ID0001"));
        System.out.println(service.selectUserById("ID0001"));
        System.out.println("redis after use : " + redisString.get("user::ID0001"));
        System.out.println();
    }

    /**
     * 测试 @Cacheable 注解，缓存list
     * 'list'：指定 list字符串作为key
     */
    @Test
    public void test002() {
        System.out.println(redisString.get("user::list"));
        List<User> list = service.selectUser();
        System.out.println(list.size());
        System.out.println(redisString.get("user::list"));
    }

    /**
     * 测试 @Cacheable 注解的 condition : 满足条件时缓存数据
     */
    @Test
    public void test003() {
        User user1 = service.selectUserByIdWithCondition("ID0002", 19);
        System.out.println(user1);
        System.out.println("redis data[ID0002] : " + redisString.get("user::ID0002"));

        User user2 = service.selectUserByIdWithCondition("ID0003", 20);
        System.out.println(user2);
        System.out.println("redis data[ID0003]: " + redisString.get("user::ID0003"));
    }

    /**
     * 测试 @Cacheable 注解的 unless : 满足条件时不缓存数据
     */
    @Test
    public void test004() {
        User user1 = service.selectUserByIdWithUnless("ID0004", 19);
        System.out.println(user1);
        System.out.println("redis data[ID0004] : " + redisString.get("user::ID0004"));

        User user2 = service.selectUserByIdWithUnless("ID0005", 20);
        System.out.println(user2);
        System.out.println("redis data[ID0005]: " + redisString.get("user::ID0005"));
    }

    /**
     * 测试 @CachePut 注解
     */
    @Test
    public void test005() {
        User user = new User("10086", "insert_name", 11);
        service.insertUser(user);
        System.out.println(redisString.get("user::10086"));

        User user2 = new User("10087", "insert_name", 22);
        service.insertUserWithCondition(user2);
        System.out.println(redisString.get("user::10087"));

        User user3 = new User("10086", "update_name", 12);
        service.updateUser(user3);
        System.out.println(redisString.get("user::10086"));
    }

    /**
     * 测试 @CacheEvict 注解
     */
    @Test
    public void test006() {
        System.out.println(redisString.getOperations().keys("user::*"));
        service.deleteUserById("10086");
        System.out.println(redisString.getOperations().keys("user::*"));
        service.deleteUserByIdAndCleanCache("10087");
        System.out.println(redisString.getOperations().keys("user::*"));
    }
}
