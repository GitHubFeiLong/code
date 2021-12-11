package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/12/11 15:12
 */
@SpringBootApplication
@MapperScan("com.mapper")
public class MPApplication {
    public static void main(String[] args) {
        SpringApplication.run(MPApplication.class, args);
    }
}
