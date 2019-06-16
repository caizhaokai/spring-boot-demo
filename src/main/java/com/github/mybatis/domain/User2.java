package com.github.mybatis.domain;

import java.util.List;

/**
 * 简单的bean，对应DB的表
 */
public class User2 {
    @Override
    public String toString() {
        return "User2 [id=" + id + ", name=" + name + ", age=" + age + "]";
    }

    private String id;
    private String name;
    private int age;
    private Login login;
    private List<Identity> identityList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public List<Identity> getIdentityList() {
        return identityList;
    }

    public void setIdentityList(List<Identity> identityList) {
        this.identityList = identityList;
    }
}
