package com.code.test;
import java.time.Instant;

import com.code.domain.UserPO;
import com.code.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/11/5 16:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {

    @Resource
    private UserRepository userRepository;

    @Test
    public void test1() {
        UserPO userPO = new UserPO();
        userPO.setName("userPo");
        userRepository.save(userPO);
    }
}
