package com.jpa.test;

import com.jpa.dao.CustomerDao;
import com.jpa.dao.LinkmanDao;
import com.jpa.domain.Customer;
import com.jpa.domain.Linkman;
import com.jpa.utils.JpaUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * 类描述：
 * 测试jpa的保存
 * @Author e-Feilong.Chen
 * @Date 2021/9/15 14:49
 */

@RunWith(SpringJUnit4ClassRunner.class) // 声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml") // 指定容器的配置信息
public class JpaOneToManyTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LinkmanDao linkmanDao;

    /**
     * 保存一个客户，保存一个联系人
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd() {
        // 床啊金一个客户，创建一个联系人
        Customer customer = new Customer();
        customer.setCustName("百度");


        Linkman linkman = new Linkman();
        linkman.setLkmName("小李");

        customer.getLinkmens().add(linkman);
        linkman.setCustomer(customer);

        customerDao.save(customer);
        linkmanDao.save(linkman);

    }
}
