package com.security.controller.oauth2.github;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2022/1/2 15:18
 */
@RestController
public class SimpleController {

    @GetMapping("/hello")
    public String hello (Principal principal) {
        return "hello," + principal.getName();
    }
}
