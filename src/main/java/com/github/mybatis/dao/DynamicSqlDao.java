package com.github.mybatis.dao;

import com.github.mybatis.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * MyBatis 动态sql
 */
@Mapper
public interface DynamicSqlDao {
    /**
     * if 对内容进行判断
     * 在注解方法中，若要使用MyBatis的动态SQL，需要编写在<script></script>标签内
     * 在 <script></script>内使用特殊符号，则使用java的转义字符，如  双引号 "" 使用&quot;&quot; 代替
     * concat函数：mysql拼接字符串的函数
     */
    @Select("<script>"
            + "select t_id, t_name, t_age                          "
            + "from sys_user                                       "
            + "<where>                                             "
            + "  <if test='id != null and id != &quot;&quot;'>     "
            + "    and t_id = #{id}                                "
            + "  </if>                                             "
            + "  <if test='name != null and name != &quot;&quot;'> "
            + "    and t_name like CONCAT('%', #{name}, '%')       "
            + "  </if>                                             "
            + "</where>                                            "
            + "</script>                                           ")
    @Results(id = "userResults", value = {
            @Result(property = "id", column = "t_id"),
            @Result(property = "name", column = "t_name"),
            @Result(property = "age", column = "t_age"),
    })
    List<User> selectUserWithIf(User user);

    /**
     * choose when otherwise 类似Java的Switch，选择某一项
     * when...when...otherwise... == if... if...else...
     */
    @Select("<script>"
            + "select t_id, t_name, t_age                                     "
            + "from sys_user                                                  "
            + "<where>                                                        "
            + "  <choose>                                                     "
            + "      <when test='id != null and id != &quot;&quot;'>          "
            + "            and t_id = #{id}                                   "
            + "      </when>                                                  "
            + "      <otherwise test='name != null and name != &quot;&quot;'> "
            + "            and t_name like CONCAT('%', #{name}, '%')          "
            + "      </otherwise>                                             "
            + "  </choose>                                                    "
            + "</where>                                                       "
            + "</script>                                                      ")
    @ResultMap("userResults")
    List<User> selectUserWithChoose(User user);

    /**
     * set 动态更新语句，类似<where>
     */
    @Update("<script>                                           "
            + "update sys_user                                  "
            + "<set>                                            "
            + "  <if test='name != null'> t_name=#{name}, </if> "
            + "  <if test='age != null'> t_age=#{age},    </if> "
            + "</set>                                           "
            + "where t_id = #{id}                               "
            + "</script>                                        ")
    int updateUserWithSet(User user);

    /**
     * foreach 遍历一个集合，常用于批量更新和条件语句中的 IN
     * foreach 批量更新
     */
    @Insert("<script>                                  "
            + "insert into sys_user                    "
            + "(t_id, t_name, t_age)                   "
            + "values                                  "
            + "<foreach collection='list' item='item'  "
            + " index='index' separator=','>           "
            + "(#{item.id}, #{item.name}, #{item.age}) "
            + "</foreach>                              "
            + "</script>                               ")
    int insertUserListWithForeach(List<User> list);

    /**
     * foreach 条件语句中的 IN
     */
    @Select("<script>"
            + "select t_id, t_name, t_age                             "
            + "from sys_user                                          "
            + "where t_name in                                        "
            + "  <foreach collection='list' item='item' index='index' "
            + "    open='(' separator=',' close=')' >                 "
            + "    #{item}                                            "
            + "  </foreach>                                           "
            + "</script>                                              ")
    @ResultMap("userResults")
    List<User> selectUserByINName(List<String> list);

    /**
     * bind 创建一个变量，绑定到上下文中
     */
    @Select("<script>                                              "
            + "<bind name=\"lname\" value=\"'%' + name + '%'\"  /> "
            + "select t_id, t_name, t_age                          "
            + "from sys_user                                       "
            + "where t_name like #{lname}                          "
            + "</script>                                           ")
    @ResultMap("userResults")
    List<User> selectUserWithBind(@Param("name") String name);
}
