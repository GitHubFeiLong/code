package com.code.openoffice.controller;

import com.code.openoffice.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/11/26 16:50
 */
@Controller
public class OpenController {
    // @Autowired
    // private DocumentConverter documentConverter;
    public static final ThreadLocal<String> TYPE = new ThreadLocal<>();
    @Resource
    private FileService fileService;

    @RequestMapping("/onlinePreview")
    public void onlinePreview(@RequestParam("url") String url, @RequestParam("type") String type,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        OpenController.TYPE.set(type);
        fileService.onlinePreview(url, response);
    }

    // @RequestMapping("/api/open-office")
    // public void demo () {
    //     documentConverter.convert(sourceFile).to(targetFile).execute();
    // }

}
