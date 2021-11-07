package com.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/11/5 15:50
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef="myAuditorAware")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
