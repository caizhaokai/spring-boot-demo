package com;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 新的测试类继承该类
 *
 * @author 蔡昭凯
 */
@RunWith(SpringRunner.class)
@SpringBootTest
// @WebAppConfiguration// web测试
public class ApplicationTestRoot {
    @Before
    public void init() {
        System.out.println(this.getClass().getName() + " start test.");
    }

    @After
    public void after() {
        System.out.println(this.getClass().getName() + " finished test.");
    }

}
