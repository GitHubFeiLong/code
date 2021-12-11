package com;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.po.UserPO;
import com.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/12/11 15:23
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MPApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MPTestApplication {

    @Resource
    UserService userService;

    /**
     * 查询
     */
    @Test
    public void testQuery() {
        List<UserPO> list = userService.list();
        list.stream().forEach(System.out::println);
    }

    /**
     * 查询分页
     */
    @Test
    public void testPage() {
        //创建Page对象
        Page<UserPO> poPage = new Page<>(1, 5);
        Page<UserPO> page = userService.page(poPage);
        System.out.println("page.getRecords() = " + page.getRecords());
        System.out.println("page = " + page);
    }

    /**
     * 插入数据
     */
    @Test
    public void testInsert() {
        UserPO userPO = new UserPO();
        userPO.setName("王五");
        userPO.setAge(25);
        userPO.setAddress("重庆沙坪坝");

        boolean save = userService.save(userPO);
        System.out.println("save = " + save);
    }

    /**
     * 更新
     */
    @Test
    public void testUpdate1() {
        UserPO userPO = new UserPO();
        userPO.setId(1469576131394613249L);
        userPO.setName("王-五");
        userPO.setAge(27);
        userPO.setAddress("重庆江北");

        boolean b = userService.saveOrUpdate(userPO);
        System.out.println("b = " + b);
    }

    /**
     * 更新
     */
    @Test
    public void testUpdate2() {
        UserPO byId = userService.getById(1L);
        byId.setName("hony");
        boolean b = userService.saveOrUpdate(byId);
        System.out.println("b = " + b);
    }


}
