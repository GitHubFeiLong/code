package com.security.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/12/27 15:31
 */
@RestController
public class HelloSecurityController {

    @RequestMapping("/")
    public String hello() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString("hello world");
    }
}
