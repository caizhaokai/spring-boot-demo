package com.github.redis;

import com.github.mybatis.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Insert("insert into sys_user       "
            + "(t_id, t_name, t_age)    "
            + "values                   "
            + "(#{id}, #{name}, ${age}) ")
    int insertUser(User bean);

    @ResultMap("redisUserDaoResults")
    @Select("select t_id, t_name, t_age "
            + "from sys_user            ")
    List<User> selectUser();

    @Select("select t_id, t_age, t_name  "
            + "from sys_user             "
            + "where t_id = #{id}        ")
    @Results(id = "redisUserDaoResults", value = {
            @Result(property = "id", column = "t_id"),
            @Result(property = "age", column = "t_age"),
            @Result(property = "name", column = "t_name"),
    })
    User selectUserById(@Param("id") String id);

    @Update("update sys_user set  "
            + "t_name = #{name},  "
            + "t_age  = #{age}    "
            + "where t_id = #{id} ")
    int updateUser(User user);

    @Delete("delete from sys_user  "
            + "where t_id = #{id}  ")
    int deleteUserById(@Param("id") String id);

}
