package com.jpa.test;

import com.jpa.dao.CustomerDao;
import com.jpa.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/9/18 12:38
 */
@RunWith(SpringJUnit4ClassRunner.class) // 声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml") // 指定容器的配置信息
public class CustomerDaoTest {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据id查询
     */
    @Test
    public void testFindOne() {
        Customer one = customerDao.findOne(3L);
        System.out.println("one = " + one);
    }

    /**
     * save:保存或者更新
     *      根据传递的对象是否存在主键id，如果没有id主键属性：保存
     *      存在id主键属性，根据id查询数据，更新数据。
     */
    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer.setCustName("黑马程序员");
        customer.setCustLevel("vip");
        customer.setCustIndustry("教育");
        Customer one = customerDao.save(customer);
        System.out.println("one = " + one);
    }

    @Test
    public void testUpdate() {
        Customer customer = new Customer();
        customer.setCustId(4L);
        customer.setCustName("黑马程序员-update");
        customer.setCustLevel("vip-update");
        Customer one = customerDao.save(customer);
        System.out.println("one = " + one);
    }

    @Test
    public void testDelete() {
        customerDao.delete(3L);
    }

    @Test
    public void testFindAll() {
        List<Customer> all = customerDao.findAll();
        all.forEach(System.out::println);
    }

    /**
     * 测试统计查询
     * 查询客户的总数量
     */
    @Test
    public void testCount() {
        long count = customerDao.count();
        System.out.println("count = " + count);
    }

    /**
     * 是否存在
     */
    @Test
    public void testExists() {
        boolean exists = customerDao.exists(3L);
        System.out.println("exists = " + exists);
    }

    /**
     * @Transactional : 保证getOne正常运行
     *
     * findOne：em.find() 立即加载
     * getOne:em.getReference 延迟加载
     *
     */
    @Transactional
    @Test
    public void testGetOne() {
        Customer one = customerDao.getOne(4L);
        System.out.println("one = " + one);
    }

    @Test
    public void testFindJpql() {
        Customer jpql = customerDao.findJpql("传智播客1");
        System.out.println("jpql = " + jpql);
    }

    @Test
    public void findCustNameAndId() {
        Customer jpql = customerDao.findCustNameAndId("传智播客1", 1L);
        System.out.println("jpql = " + jpql);
    }

    /**
     * 注意：需要事务
     * spring data jpa 使用jpql完成 更新/删除操作
     *      需要手动添加事务的支持
     *      默认执行完成后，事务会自动回滚。（使用@Rollback(value = false) 关闭自动回滚）
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void updateCustomer() {
        customerDao.updateCustomer("修改", 4L);
        Customer one = customerDao.findOne(4L);
        System.out.println("one = " + one);
    }

    @Test
    public void testFindSql() {
        List<Object[]> sql = customerDao.findSql();
        sql.forEach(p-> System.out.println(Arrays.toString(p)));
    }

    @Test
    public void findSqlLike() {
        List<Object[]> sql = customerDao.findSqlLike("传智%");
        sql.forEach(p-> System.out.println(Arrays.toString(p)));
    }

    @Test
    public void findByCustName() {
        Customer custName = customerDao.findByCustName("传智播客1");
        System.out.println("custName = " + custName);
    }
}
