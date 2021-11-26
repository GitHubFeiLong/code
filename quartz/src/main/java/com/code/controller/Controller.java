package com.code.controller;

import com.code.service.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/11/26 8:33
 */
@org.springframework.stereotype.Controller
@RequestMapping("/api/pdf")
public class Controller {

    @Resource
    private FileService fileService;

    @RequestMapping("/onlinePreview")
    public void onlinePreview(@RequestParam("url") String url, HttpServletResponse response) throws Exception {
        fileService.onlinePreview(url,response);
    }


}
