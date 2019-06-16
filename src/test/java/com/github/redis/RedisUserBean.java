package com.github.redis;

import com.ApplicationTestRoot;
import com.github.mybatis.domain.User;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 测试配置的 com.github.redis.RedisConfig 配置的 RedisTemplate<String, Object>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisUserBean extends ApplicationTestRoot {
	@Autowired
	private RedisTemplate<String, Object> template;
	
	@Autowired
	private ValueOperations<String, Object> redisString;
	
	@Autowired
	private HashOperations<String, String, Object> redisHash;
	
	@Autowired
	private ListOperations<String, Object> redisList;
	
	@Autowired
	private SetOperations<String, Object> redisSet;
	
	@Autowired
	private ZSetOperations<String, Object> redisZSet;

	@Test
	public void test001() {
		System.out.println(template);// not null
	}

	/**
	 * 测试 Redis String
	 */
	@Test
	public void test002() {
		// SET key value: 设置指定 key 的值
		redisString.set("strKey1", "hello spring boot redis");
		// GET key: 获取指定 key 的值
		String value = (String) redisString.get("strKey1");
		System.out.println(value);

		redisString.set("strKey2", new User("ID10086", "theName", 11));
		User user = (User) redisString.get("strKey2");
		System.out.println(user);
	}

	/**
	 * 测试 Redis Hash
	 */
	@Test
	public void test003() {
		Map<String, Object> map = new HashMap<>();
		map.put("id", "10010");
		map.put("name", "redis_name");
		map.put("amount", 12.34D);
		map.put("age", 11);
		redisHash.putAll("hashKey", map);
		// HGET key field 获取存储在哈希表中指定字段的值
		String name = (String) redisHash.get("hashKey", "name");
		System.out.println(name);
		// HGET key field
		Double amount = (Double) redisHash.get("hashKey", "amount");
		System.out.println(amount);
		// HGETALL key 获取在哈希表中指定 key 的所有字段和值
		Map<String, Object> map2 = redisHash.entries("hashKey");
		System.out.println(map2);
		// HKEYS key 获取在哈希表中指定 key 的所有字段
		Set<String> keySet = redisHash.keys("hashKey");
		System.out.println(keySet);
		// HVALS key 获取在哈希表中指定 key 的所有值
		List<Object> valueList = redisHash.values("hashKey");
		System.out.println(valueList);
	}

	/**
	 * 测试 Redis List
	 */
	@Test
	public void test004() {
		redisList.leftPush("listKey", "aaa");
		redisList.leftPush("listKey", "bbb");
		redisList.rightPush("listKey", "ccc");
		// LLEN key: 获取列表长度
		Long size = redisList.size("listKey");
		// LRANGE key start stop: 获取列表指定范围内的元素
		if (size != null) {
			List <Object> list = redisList.range("listKey", 0, size);
			System.out.println(size);
			System.out.println(list);
		}
	}

	/**
	 * 测试 Redis Set
	 */
	@Test
	public void test005() {
		redisSet.add("setKey", "aSet");
		redisSet.add("setKey", "bSet");
		redisSet.add("setKey", "bSet");
		// SMEMBERS key: 返回集合中的所有成员
		Set<Object> set = redisSet.members("setKey");
		System.out.println(set);
	}

	/**
	 * 测试 Redis ZSet
	 */
	@Test
	public void test006() {
		// ZADD key score1 member1 [score2 member2] 
		// 向有序集合添加一个或多个成员，或者更新已存在成员的分数，
		// 注意score位置，redisZSet 和 ZADD 命令不同
		redisZSet.add("zSetKey", "aZSet", 11);
		redisZSet.add("zSetKey", "bZSet", 12);
		redisZSet.add("zSetKey", "cZSet", 13);
		redisZSet.add("zSetKey", "dZSet", 14);
		// ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT] 
		// 通过分数返回有序集合指定区间内的成员
		Set<Object> set = redisZSet.rangeByScore("zSetKey", 11, 13);
		System.out.println(set);
	}
}
