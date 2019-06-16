package com.github.mybatis.providerDao.sqlBuilder;

import com.github.mybatis.domain.User;
import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;


public class UserBuilder2 implements ProviderMethodResolver {

    private final static String TABLE_NAME = "sys_user";

    public static String selectUserById() {
        return new SQL() {{
            SELECT("t_id, t_name, t_age");
            FROM(TABLE_NAME);
            WHERE("t_id = #{id}");
        }}.toString();
    }

    public static String selectUser(String name) {
        return new SQL() {{
            SELECT("t_id, t_name, t_age");
            FROM(TABLE_NAME);
            if (name != null && name != "") {
                WHERE("t_name like CONCAT('%', #{name}, '%')");
            }
        }}.toString();
    }

    public static String insertUser() {
        return new SQL() {{
            INSERT_INTO(TABLE_NAME);
            INTO_COLUMNS("t_id, t_name, t_age");
            INTO_VALUES("#{id}, #{name}, #{age}");
        }}.toString();
    }

    public static String updateUser(User user) {
        return new SQL() {{
            UPDATE(TABLE_NAME);
            SET("t_name = #{name}", "t_age = #{age}");
            WHERE("t_id = #{id}");
        }}.toString();
    }

    public static String deleteUser(final String id) {
        return new SQL() {{
            DELETE_FROM(TABLE_NAME);
            WHERE("t_id = #{id}");
        }}.toString();
    }
}
