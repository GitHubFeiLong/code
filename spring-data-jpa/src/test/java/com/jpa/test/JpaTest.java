package com.jpa.test;

import com.jpa.domain.Customer;
import com.jpa.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 类描述：
 * 测试jpa的保存
 * @Author e-Feilong.Chen
 * @Date 2021/9/15 14:49
 */
public class JpaTest {

    /**
     * 测试jpa的保存
     * 步骤：
     *  1. 加载配置文件创建工厂（实体管理器工厂）对象
     *  2. 通过尸体管理类工厂获取实体管理器
     *  3. 获取事务对象，开启事务
     *  4. 完成增删改查操作
     *  5. 提交事务（回滚事务）
     *  6. 释放资源
     */
    @Test
    public void testSave() {
        // // 1. 加载配置文件创建工厂（实体管理器工厂）对象
        // EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
        // // 2. 通过尸体管理类工厂获取实体管理器
        // EntityManager entityManager = factory.createEntityManager();
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 3. 获取事务对象，开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 完成增删改查操作
        Customer customer = new Customer();
        customer.setCustName("name");
        customer.setCustIndustry("教育");

        // 保存
        entityManager.persist(customer);
        // 5. 提交事务（回滚事务）
        transaction.commit();
        // 6. 释放资源
        entityManager.close();
        // factory.close();
    }

    /**
     * 根据id查询客户
     * 使用find方法查询：
     *  1. 查询的对象就是当前客户对象本身
     *  2. 在调用find方法的时候，就会发送sql语句查询数据库
     *  立即加载
     */
    @Test
    public void testFind() {
        // 通过工具类 获取EntityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // option
        /*
            find 根据id查询数据
            class：查询数据的结果需要封装的实体类型的字节码
            id：主键值
         */
        Customer customer = entityManager.find(Customer.class, 1L);

        System.out.println("customer = " + customer);
        // 提交事务
        transaction.commit();
        // 释放资源
        entityManager.close();
    }

    /**
     * 根据id查询客户
     * 使用getReference方法
     *      1. 获取的对象是一个动态代理对象
     *      2. 调用 getReference 方法不会立即发送sql查询数据库
     *      当调用查询结果对象的时候，才会发送查询的sql语句，什么时候用什么时候发送
     *  延迟加载（懒加载）
     *      得到的是一个动态代理对象。
     *      什么时候用，什么时候加载。
     */
    @Test
    public void testReference() {
        // 通过工具类 获取EntityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // option
        /*
            Reference 根据id查询数据
            class：查询数据的结果需要封装的实体类型的字节码
            id：主键值
         */
        Customer customer = entityManager.getReference(Customer.class, 1L);
        System.out.println("customer = " + customer);
        // 提交事务
        transaction.commit();
        // 释放资源
        entityManager.close();
    }

    /**
     * 更新客户操作
     */
    @Test
    public void testUpdate() {
        // 通过工具类 获取EntityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // option
        // 查询客户
        Customer customer = entityManager.getReference(Customer.class, 1L);
        // 更新客户
        customer.setCustIndustry("测试修改");
        entityManager.merge(customer);
        System.out.println("customer = " + customer);
        // 提交事务
        transaction.commit();
        // 释放资源
        entityManager.close();
    }
}
