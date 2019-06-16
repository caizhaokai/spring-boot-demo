package com.github.mybatis.service;

import com.github.mybatis.dao.SimpleSqlDao;
import com.github.mybatis.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

/**
 * SpringBoot + MyBatis事务管理
 */
@Service
public class TransactionalService {

    @Autowired
    private SimpleSqlDao dao;

    // 手动控制事务
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    /**
     * 测试DB执行sql语句时出现异常的事务控制
     *
     * @Transactional 的事务都是自动提交的(commit)，遇到异常时回滚(rollback)
     */
    @Transactional
    public void testTransactional(boolean exceptionFlag) {
        dao.deleteUserById("10010");
        User user = new User("10010", "first_data", 11);
        dao.insertUser(user);
        // 重复的主键，DB会报主键冲突的异常
        if (exceptionFlag) {
            user.setName("doublePrimaryKey");
            dao.insertUser(user);
        }
    }

    /**
     * @Transactional 的参数
     * value                   |String                        | 可选的限定描述符，指定使用的事务管理器
     * propagation             |Enum: Propagation             | 可选的事务传播行为设置
     * isolation               |Enum: Isolation               | 可选的事务隔离级别设置
     * readOnly                |boolean                       | 读写或只读事务，默认读写
     * timeout                 |int (seconds)                 | 事务超时时间设置
     * rollbackFor             |Class<? extends Throwable>[]  | 导致事务回滚的异常类数组
     * rollbackForClassName	   |String[]                      | 导致事务回滚的异常类名字数组
     * noRollbackFor           |Class<? extends Throwable>[]  | 不会导致事务回滚的异常类数组
     * noRollbackForClassName  |String[]                      | 不会导致事务回滚的异常类名字数组
     */
    @Transactional(timeout = 4)
    public void testTSTimeout() {
        System.out.println(dao.selectUserById("10010"));
        User user = new User("10010", "timeout_name", 12);
        dao.updateUser(user);
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(dao.selectUserById("10010"));
    }

    /**
     * 测试手动提交事务
     */
    public void testHandleCommitTS(boolean exceptionFlag) {
//		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//		transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // 开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            dao.deleteUserById("10010");
            User user = new User("10010", "first_data", 11);
            dao.insertUser(user);
            // 重复的主键，DB会报主键冲突的异常
            if (exceptionFlag) {
                user.setName("doublePrimaryKey");
                dao.insertUser(user);
            }
            // 提交事务
            dataSourceTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            // 回滚事务
            dataSourceTransactionManager.rollback(transactionStatus);
            throw e;
        }
    }
}
