# OpenOffice 使用
简单的说一下使用教程，使用openoffice可以让常见的文件转换为pdf，从而实现在线预览文件的功能。
## 前要步骤
使用前需要执行一些准备工作：安装openoffice、使用maven创建spring boot项目，并导入相关依赖、编写转代码。

### 安装open office

前往[官网](https://www.openoffice.org/zh-cn/download/)进行下载open office，需要记住安装路径，稍后项目需要配置该路径。

### 配置项目

1. 导入maven依赖

```xml
<!--springboot支持包，里面包括了自动配置类 -->
<dependency>
    <groupId>org.jodconverter</groupId>
    <artifactId>jodconverter-spring-boot-starter</artifactId>
    <version>4.2.2</version>
</dependency>
<!--jodconverter 本地支持包 -->
<dependency>
    <groupId>org.jodconverter</groupId>
    <artifactId>jodconverter-local</artifactId>
    <version>4.2.2</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.artofsolving/jodconverter -->
<dependency>
    <groupId>com.artofsolving</groupId>
    <artifactId>jodconverter</artifactId>
    <version>2.2.1</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

2. 配置配置文件

   ```properties
   jodconverter.local.enabled=true
   #home:安装地址
   jodconverter.local.office-home=D:\\application\\openoffice home
   jodconverter.local.max-tasks-per-process=10
   jodconverter.local.port-numbers=8100
   ```

   
