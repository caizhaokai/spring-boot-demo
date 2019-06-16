package com.github.redis;

import com.ApplicationTestRoot;
import com.google.gson.Gson;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;

/**
 * 测试redis org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration 类配置的bean
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisSysBean extends ApplicationTestRoot {
    @Autowired
    private RedisConnectionFactory factory;

    @Autowired
    private RedisTemplate<Object, Object> objectTemplate;

    @Autowired
    private StringRedisTemplate stringTemplate;

    /**
     * 测试ping命令
     */
    @Test
    public void test001() {
        String callback = factory.getConnection().ping();
        System.out.println(callback);
    }

    /**
     * 测试SpringBoot 配置的RedisTemplate 对象
     */
    @Test
    public void test002() {
        System.out.println(objectTemplate);// not null
        System.out.println(stringTemplate);// not null
    }

    /**
     * objectTemplate：key和value需要实现Serializable 接口
     */
    @Test
    public void test003() {
        ValueOperations<Object, Object> valueTemplate = objectTemplate.opsForValue();

        valueTemplate.set("ObjectKey1", "hello spring boot redis, Object Redis");
        String value = (String) valueTemplate.get("ObjectKey1");
        System.out.println(value);

        valueTemplate.set("ObjectKey2", new Person("theName", 11));
        Person person = (Person) valueTemplate.get("ObjectKey2");
        System.out.println(person);
    }

    /**
     * stringTemplate: key和value都是string
     * 当需要保存bean时，可以转为json格式的字符串
     */
    @Test
    public void test004() {
        ValueOperations<String, String> valueTemplate = stringTemplate.opsForValue();
        Gson gson = new Gson();

        valueTemplate.set("StringKey1", "hello spring boot redis, String Redis");
        String value = valueTemplate.get("StringKey1");
        System.out.println(value);

        valueTemplate.set("StringKey2", gson.toJson(new Person("theName", 11)));
        Person person = gson.fromJson(valueTemplate.get("StringKey2"), Person.class);
        System.out.println(person);
    }
}

class Person implements Serializable {
    @Override
    public String toString() {
        return "Persion [name=" + name + ", age=" + age + "]";
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}