package security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * MapperScan 可以不在mapper层添加注解
 * @author msi
 * @Date 2021-04-09 11:06
 * @Version 1.0
 */
@SpringBootApplication
@EntityScan("security.po")
@EnableJpaAuditing
@EnableJpaRepositories
public class SecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }
}
