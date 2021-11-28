// package com.code.openoffice.controller;
//
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.InputStream;
//
// import javax.servlet.ServletOutputStream;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.apache.commons.io.IOUtils;
// import org.jodconverter.DocumentConverter;
// import org.jodconverter.office.OfficeException;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpRequest;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.ResponseBody;
//
// /**
//  * 类描述：
//  *
//  * @Author e-Feilong.Chen
//  * @Date 2021/11/26 17:17
//  */
// @Controller
// public class MyController {
//
//     // 第一步：转换器直接注入
//     @Autowired
//     private DocumentConverter converter;
//
//     @Autowired
//     private HttpServletResponse response;
//
//     @RequestMapping("toPdfFile")
//     public String toPdfFile() {
//         // File file = new File("src/main/java/com/lcf/controller/lala.doc");//需要转换的文件
//         // File file = new File("D:\\文档\\冉爽\\MES项目测试计划V1.0.docx");//需要转换的文件
//         File file = new File("D:\\文档\\冉爽\\MES制造运营管理系统v1.0.1版本迭代测试系统功能测试报告.docx");//需要转换的文件
//         try {
//             File newFile = new File("D:/obj-pdf");//转换之后文件生成的地址
//             if (!newFile.exists()) {
//                 newFile.mkdirs();
//             }
//             //文件转化
//             converter.convert(file).to(new File("D:/obj-pdf/hello.pdf")).execute();
//             //使用response,将pdf文件以流的方式发送的前段
//             ServletOutputStream outputStream = response.getOutputStream();
//             InputStream in = new FileInputStream(new File("D:/obj-pdf/hello.pdf"));// 读取文件
//             // copy文件
//             int i = IOUtils.copy(in, outputStream);
//             System.out.println(i);
//             in.close();
//             outputStream.close();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//         return "This is to pdf";
//     }
//
// }
//
//
