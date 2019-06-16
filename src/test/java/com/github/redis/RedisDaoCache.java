package com.github.redis;

import com.ApplicationTestRoot;
import com.github.mybatis.domain.User;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

public class RedisDaoCache extends ApplicationTestRoot {
	@Autowired
	private RedisCacheUserDao dao;

	@Autowired
	private ValueOperations<String, Object> redisString;

	@Test
	public void test001() {
		System.out.println("redis before use : " + redisString.get("users::ID0001"));
		System.out.println(dao.selectUserById("ID0001"));
		System.out.println("redis after use : " + redisString.get("users::ID0001"));
		System.out.println();

		System.out.println(redisString.get("users::list"));
		List<User> list = dao.selectUser();
		System.out.println(list.size());
		System.out.println(redisString.get("users::list"));
	}
}
