package com.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/12/27 16:21
 */
@RestController
@RequestMapping("/user/api")
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "hello, user";
    }
}
