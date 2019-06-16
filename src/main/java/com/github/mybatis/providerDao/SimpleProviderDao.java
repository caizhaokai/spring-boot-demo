package com.github.mybatis.providerDao;

import com.github.mybatis.domain.User;
import com.github.mybatis.providerDao.sqlBuilder.UserBuilder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SimpleProviderDao {
    /**
     * @see UserBuilder#selectUserById()
     */
    @Results(id = "userResults", value = {
            @Result(property = "id", column = "t_id"),
            @Result(property = "name", column = "t_name"),
            @Result(property = "age", column = "t_age"),
    })
    @SelectProvider(type = UserBuilder.class)
    User selectUserById(String id);

    /**
     * @see UserBuilder#selectUser(String)
     */
    @ResultMap("userResults")
    @SelectProvider(type = UserBuilder.class)
    List<User> selectUser(String name);

    /**
     * @see UserBuilder#insertUser()
     */
    @InsertProvider(type = UserBuilder.class)
    int insertUser(User user);

    /**
     * @see UserBuilder#insertUserList(List)
     */
    @InsertProvider(type = UserBuilder.class)
    int insertUserList(List<User> list);

    /**
     * @see UserBuilder#updateUser()
     */
    @UpdateProvider(type = UserBuilder.class)
    int updateUser(User user);

    /**
     * @see UserBuilder#deleteUser()
     */
    @DeleteProvider(type = UserBuilder.class)
    int deleteUser(String id);
}
	

