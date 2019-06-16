package com.github.mybatis.dao;

import com.github.mybatis.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 最基本的增删改查
 */
@Mapper
public interface SimpleSqlDao {
    /**
     * 测试连接
     */
    @Select("select 1 from dual")
    int testSqlConnent();

    /**
     * 新增，参数是一个bean
     */
    @Insert("insert into sys_user       "
            + "(t_id, t_name, t_age)    "
            + "values                   "
            + "(#{id}, #{name}, ${age}) ")
    int insertUser(User bean);

    /**
     * 新增，参数是一个Map
     */
    @Insert("insert into sys_user       "
            + "(t_id, t_name, t_age)    "
            + "values                   "
            + "(#{id}, #{name}, ${age}) ")
    int insertUserByMap(Map<String, Object> map);

    /**
     * 新增，参数是多个值，需要使用@Param来修饰
     * MyBatis 的参数使用的@Param的字符串，一般@Param的字符串与参数相同
     */
    @Insert("insert into sys_user       "
            + "(t_id, t_name, t_age)    "
            + "values                   "
            + "(#{id}, #{name}, ${age}) ")
    int insertUserByParam(@Param("id") String id,
                          @Param("name") String name,
                          @Param("age") int age);

    /**
     * 修改
     */
    @Update("update sys_user set  "
            + "t_name = #{name},  "
            + "t_age  = #{age}    "
            + "where t_id = #{id} ")
    int updateUser(User bean);

    /**
     * 删除
     */
    @Delete("delete from sys_user  "
            + "where t_id = #{id}  ")
    int deleteUserById(@Param("id") String id);

    /**
     * 删除
     */
    @Delete("delete from sys_user ")
    int deleteUserAll();

    /**
     * truncate 返回值为0
     */
    @Delete("truncate table sys_user ")
    void truncateUser();

    /**
     * 查询bean
     * 映射关系@Results
     *
     * @Result(property="java Bean name", column="DB column name"),
     */
    @Select("select t_id, t_age, t_name  "
            + "from sys_user             "
            + "where t_id = #{id}        ")
    @Results(id = "userResults", value = {
            @Result(property = "id", column = "t_id"),
            @Result(property = "age", column = "t_age"),
            @Result(property = "name", column = "t_name"),
    })
    User selectUserById(@Param("id") String id);

    /**
     * 查询List
     */
    @ResultMap("userResults")
    @Select("select t_id, t_name, t_age "
            + "from sys_user            ")
    List<User> selectUser();

    @Select("select count(*) from sys_user ")
    int selectCountUser();
}
