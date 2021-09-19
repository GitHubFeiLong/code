package com.jpa.test;

import com.jpa.dao.RoleDao;
import com.jpa.dao.UserDao;
import com.jpa.domain.Role;
import com.jpa.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/9/19 20:58
 */
@RunWith(SpringJUnit4ClassRunner.class) // 声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml") // 指定容器的配置信息
public class UserRoleTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 测试添加
     */
    @Test
    public void testAdd() {
        User user = new User();
        user.setUsername("张三");
        user.setPassword("123456");

        Role role = new Role();
        role.setRoleName("管理员");
        user.getRoles().add(role);

        userDao.save(user);
    }

    /**
     * 测试查询
     */
    @Test
    @Transactional
    public void testGetOne() {
        User one = userDao.getOne(1L);
        System.out.println("one = " + one);
        Set<Role> roles = one.getRoles();
        roles.forEach(System.out::println);
    }

    /**
     * 测试查询
     */
    @Test
    @Transactional
    public void testFindOne() {
        User one = userDao.findOne(1L);
        System.out.println("one = " + one);
        Set<Role> roles = one.getRoles();
        roles.forEach(System.out::println);
    }
}
