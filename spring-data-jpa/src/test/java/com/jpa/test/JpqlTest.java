package com.jpa.test;

import com.jpa.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * 类描述：
 * 测试 jpql查询
 * @Author e-Feilong.Chen
 * @Date 2021/9/15 14:49
 */
public class JpqlTest {

    /**
     * 查询全部
     *      jpql：from Customer
     *      sql：select * from cst_customer
     */
    @Test
    public void testFindAll() {
        // 1.获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 查询
        String jpql = "from Customer";
        // 创建Query查询对象，query对象才是执行jpql的对象
        Query query = entityManager.createQuery(jpql);

        // 发送查询，封装结果集
        List resultList = query.getResultList();

        resultList.stream().forEach(System.out::println);

        // 4.提交事务
        transaction.commit();
        // 5.释放资源
        entityManager.close();
    }

    /**
     * 排序查询
     * 根据id倒序
     *      jpql：from Customer order by custId desc
     *      sql: select * from cst_customer order by cust_id desc
     * 总结jpql查询
     * 1. 创建query查询对象
     * 2. 对参数进行赋值
     * 3. 查询，并得到返回结果
     */
    @Test
    public void testOrder() {
        // 1.获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 查询
        String jpql = "from Customer order by custId desc";
        // 创建Query查询对象，query对象才是执行jpql的对象
        Query query = entityManager.createQuery(jpql);

        // 发送查询，封装结果集
        List resultList = query.getResultList();

        resultList.stream().forEach(System.out::println);

        // 4.提交事务
        transaction.commit();
        // 5.释放资源
        entityManager.close();
    }

    /**
     * 使用jpql查询，统计客户的总数
     * sql：select count(cust_id) from cst_customer
     * jpql：select count(custId) from Customer
     */
    @Test
    public void testCount() {
        // 1.获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 查询
        // i:根据jpql语句创建Query查询对象
        String jpql = "select count(custId) from Customer";
        // 创建Query查询对象，query对象才是执行jpql的对象
        Query query = entityManager.createQuery(jpql);
        // ii:对参数赋值
        // iii: 发送查询,并封装结果集
        /*
            getResultList：将结果封装为list集合
            getSingleResult：得到唯一的结果集
         */
        Object singleResult = query.getSingleResult();

        System.out.println("singleResult = " + singleResult);
        // 4.提交事务
        transaction.commit();
        // 5.释放资源
        entityManager.close();
    }

    /**
     * 分页查询
     * sql：select * from cst_customer limit ?, ?
     * jpql:from Customer
     */
    @Test
    public void testPage() {
        // 1.获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 查询
        // i:根据jpql语句创建Query查询对象
        String jpql = "from Customer";
        // 创建Query查询对象，query对象才是执行jpql的对象
        Query query = entityManager.createQuery(jpql);
        // ii:对参数赋值 -- 分页参数
        // 起始索引
        query.setFirstResult(0);
        // 每页查询的条数
        query.setMaxResults(2);

        // iii: 发送查询,并封装结果集
        /*
            getResultList：将结果封装为list集合
            getSingleResult：得到唯一的结果集
         */
        List resultList = query.getResultList();

        resultList.forEach(System.out::println);

        // 4.提交事务
        transaction.commit();
        // 5.释放资源
        entityManager.close();
    }

    /**
     * 条件查询
     *  案例：查询客户名称以 传智播客 开头的客户
     *  sql： select * from cst_customer where cust_name like ?
     *  jpql： from Customer where custName like ?
     */
    @Test
    public void testCodition() {
        // 1.获取EntityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        // 2. 开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 3. 查询
        // i:根据jpql语句创建Query查询对象
        String jpql = "from Customer where custName like ?";
        // 创建Query查询对象，query对象才是执行jpql的对象
        Query query = entityManager.createQuery(jpql);
        // ii:对参数赋值 -- 占位符参数
        // 第一个参数：占位符的索引位置（从1开始），第二个参数取值
        query.setParameter(1, "传智播客%");

        // iii: 发送查询,并封装结果集
        /*
            getResultList：将结果封装为list集合
            getSingleResult：得到唯一的结果集
         */
        List resultList = query.getResultList();

        resultList.forEach(System.out::println);

        // 4.提交事务
        transaction.commit();
        // 5.释放资源
        entityManager.close();
    }
}
