package com.github.mybatis.providerDao.sqlBuilder;

import com.github.mybatis.domain.User;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;


/*
 * ProviderMethodResolver接口属于比较新的api，如果找不到这个接口，更新你的mybatis的版本
 * 继承ProviderMethodResolver接口，Mapper中可以直接指定该类即可，
 * 但对应的Mapper的方法名要更Builder的方法名相同
 *
 * Mapper:
 * @SelectProvider(type = UserBuilder.class)
 * public User selectUserById(String id);
 *
 * UserBuilder:
 * public static String selectUserById(final String id)...
 */
public class UserBuilder implements ProviderMethodResolver {

    private final static String TABLE_NAME = "sys_user";

    public static String selectUserById() {
        return new SQL()
                .SELECT("t_id, t_name, t_age")
                .FROM(TABLE_NAME)
                .WHERE("t_id = #{id}")
                .toString();
    }

    public static String selectUser(String name) {
        SQL sql = new SQL()
                .SELECT("t_id, t_name, t_age")
                .FROM(TABLE_NAME);
        if (name != null && name != "") {
            sql.WHERE("t_name like CONCAT('%', #{name}, '%')");
        }
        return sql.toString();
    }

    public static String insertUser() {
        return new SQL()
                .INSERT_INTO(TABLE_NAME)
                .INTO_COLUMNS("t_id, t_name, t_age")
                .INTO_VALUES("#{id}, #{name}, #{age}")
                .toString();
    }

    /**
     * 使用SQL Builder进行批量插入
     * 关键是sql语句中的values格式书写和访问参数
     * values格式：values (?, ?, ?) , (?, ?, ?) , (?, ?, ?) ...
     * 访问参数：MyBatis只能读取到list参数，所有使用list[i].Filed访问变量，如 #{list[0].id}
     */
    public static String insertUserList(List<User> list) {
        SQL sql = new SQL()
                .INSERT_INTO(TABLE_NAME)
                .INTO_COLUMNS("t_id, t_name, t_age");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(") , (");
            }
            sb.append("#{list[");
            sb.append(i);
            sb.append("].id}, ");
            sb.append("#{list[");
            sb.append(i);
            sb.append("].name}, ");
            sb.append("#{list[");
            sb.append(i);
            sb.append("].age}");
        }
        sql.INTO_VALUES(sb.toString());
        return sql.toString();
    }

    public static String updateUser() {
        return new SQL()
                .UPDATE(TABLE_NAME)
                .SET("t_name = #{name}", "t_age = #{age}")
                .WHERE("t_id = #{id}")
                .toString();
    }

    public static String deleteUser() {
        return new SQL()
                .DELETE_FROM(TABLE_NAME)
                .WHERE("t_id = #{id}")
                .toString();
    }
}
