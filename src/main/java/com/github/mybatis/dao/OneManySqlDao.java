package com.github.mybatis.dao;

import com.github.mybatis.domain.Identity;
import com.github.mybatis.domain.Login;
import com.github.mybatis.domain.User2;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * Mybatis 对一，对多查询
 */
@Mapper
public interface OneManySqlDao {
    /**
     * 对@Result的解释
     * property：java bean 的成员变量
     * column：对应查询的字段，也是传递到对应子查询的参数，传递多参数使用Map column = "{param1=SQL_COLUMN1,param2=SQL_COLUMN2}"
     * one=@One：对一查询
     * many=@Many：对多查询
     * 属性select：需要查询的方法，全称
     */
    @Select("select t_id, t_name, t_age "
            + "from sys_user            "
            + "where t_id = #{id}       ")
    @Results({
            @Result(property = "id", column = "t_id"),
            @Result(property = "name", column = "t_name"),
            @Result(property = "age", column = "t_age"),
            @Result(property = "login", column = "t_id",
                    one = @One(select = "selectLoginById", fetchType = FetchType.EAGER)),
            @Result(property = "identityList", column = "t_id",
                    many = @Many(select = "selectIdentityById", fetchType = FetchType.EAGER)),
    })
    User2 selectUser(@Param("id") String id);

    /**
     * 对一 子查询
     */
    @Select("select t_username, t_password "
            + "from sys_login              "
            + "where t_id = #{id}          ")
    @Results(id = "loginResults", value = {
            @Result(property = "username", column = "t_username"),
            @Result(property = "password", column = "t_password"),
    })
    Login selectLoginById(@Param("id") String id);

    /**
     * 对多 子查询
     */
    @Select("select t_id_type, t_id_no "
            + "from sys_identity              "
            + "where t_id = #{id}          ")
    @Results(id = "identityResults", value = {
            @Result(property = "idType", column = "t_id_type"),
            @Result(property = "idNo", column = "t_id_no"),
    })
    List<Identity> selectIdentityById(@Param("id") String id);

    @Delete("delete from sys_login where t_id = #{id} ")
    int deleteLogin(@Param("id") String id);

    @Delete("delete from sys_identity where t_id = #{id} ")
    int deleteIdentity(@Param("id") String id);

    @Insert("insert into sys_login               "
            + "(t_id, t_username, t_password )   "
            + "values                            "
            + "(#{id}, #{username}, #{password}) ")
    int insertLogin(@Param("id") String id,
                    @Param("username") String username,
                    @Param("password") String password);

    @Insert("insert into sys_identity      "
            + "(t_id, t_id_type, t_id_no ) "
            + "values                      "
            + "(#{id}, #{idType}, #{idNo}) ")
    int insertIdentity(@Param("id") String id,
                       @Param("idType") String idType,
                       @Param("idNo") String idNo);
}
