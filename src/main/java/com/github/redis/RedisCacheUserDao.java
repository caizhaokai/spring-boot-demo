package com.github.redis;

import com.github.mybatis.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Mapper
@CacheConfig(cacheNames = "users")
public interface RedisCacheUserDao {
    @Cacheable(key ="#id")
    @Select("select t_id, t_age, t_name  "
            + "from sys_user             "
            + "where t_id = #{id}        ")
    @Results(id = "redisUserDaoResults", value = {
            @Result(property = "id", column = "t_id"),
            @Result(property = "age", column = "t_age"),
            @Result(property = "name", column = "t_name"),
    })
    User selectUserById(@Param("id") String id);

    @Cacheable(key ="'list'")
    @ResultMap("redisUserDaoResults")
    @Select("select t_id, t_name, t_age "
            + "from sys_user            ")
    List<User> selectUser();
}
