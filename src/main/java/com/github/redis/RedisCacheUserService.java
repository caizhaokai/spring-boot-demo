package com.github.redis;

import com.github.mybatis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 指定默认缓存区
 * 缓存区：key的前缀，与指定的key构成redis的key，如 user::10001
 */
@CacheConfig(cacheNames = "user")
@Service
public class RedisCacheUserService {

    @Autowired
    private UserDao dao;

    /**
     * @Cacheable 缓存有数据时，从缓存获取；没有数据时，将返回值保存到缓存中
     * @Cacheable 一般在查询中使用
     * 1) cacheNames 指定缓存区，没有配置使用@CacheConfig指定的缓存区
     * 2) key 指定缓存区的key
     * 3) 注解的值使用SpEL表达式
     * eq ==
     * lt <
     * le <=
     * gt >
     * ge >=
     */
    @Cacheable(cacheNames = "user", key = "#id")
    public User selectUserById(String id) {
        return dao.selectUserById(id);
    }

    @Cacheable(key="'list'")
    public List<User> selectUser() {
        return dao.selectUser();
    }

    /**
     * condition 满足条件缓存数据
     */
    @Cacheable(key = "#id", condition = "#number ge 20") // >= 20
    public User selectUserByIdWithCondition(String id, int number) {
        return dao.selectUserById(id);
    }

    /**
     * unless 满足条件时否决缓存数据
     */
    @Cacheable(key = "#id", unless = "#number lt 20") // < 20
    public User selectUserByIdWithUnless(String id, int number) {
        return dao.selectUserById(id);
    }

    /**
     * @CachePut 将返回值保存到缓存中
     * @CachePut 一般在新增和修改中使用
     */
    @CachePut(key = "#user.id")
    public User insertUser(User user) {
        dao.insertUser(user);
        return user;
    }

    @CachePut(key = "#user.id", condition = "#user.age ge 20")
    public User insertUserWithCondition(User user) {
        dao.insertUser(user);
        return user;
    }

    @CachePut(key = "#user.id")
    public User updateUser(User user) {
        dao.updateUser(user);
        return user;
    }

    /**
     * 根据key删除缓存区中的数据
     */
    @CacheEvict(key = "#id")
    public void deleteUserById(String id) {
        dao.deleteUserById(id);
    }

    /**
     * allEntries = true ：删除整个缓存区的所有值，此时指定的key无效
     * beforeInvocation = true ：默认false，表示调用方法之后删除缓存数据；true时，在调用之前删除缓存数据(如方法出现异常)
     */
    @CacheEvict(key = "#id", allEntries = true)
    public void deleteUserByIdAndCleanCache(String id) {
        dao.deleteUserById(id);
    }
}

