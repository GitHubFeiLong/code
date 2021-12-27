package com.security.config;

import com.security.service.MyUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 类描述：
 * security 配置
 *
 * @author msi
 * @version 1.0
 * @date 2021/12/21 21:35
 */
// @Slf4j
// @Configuration
// @EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailService myUserDetailService;

    public WebSecurityConfig(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启自动配置的登录功能
        http.formLogin() //开启登录
                //自定义登录请求路径(post)
                .loginProcessingUrl("/user/login")
                //自定义登录用户名密码属性名,默认为username和password
                .usernameParameter("username").passwordParameter("password")
                //验证成功处理器(前后端分离)：返回状态码200
                // .successHandler(authenticationSuccessHandler)
                //验证失败处理器(前后端分离)：返回状态码402
                // .failureHandler(authenticationFailureHandler)
                //身份验证详细信息源(登录验证中增加额外字段)
                // .authenticationDetailsSource(authenticationDetailsSource)
                .permitAll();
        // 开启自动配置的注销功能
        http.logout() //用户注销, 清空session
                //自定义注销请求路径
                .logoutUrl("/user/logout");
                //注销成功处理器(前后端分离)：返回状态码200
                // .logoutSuccessHandler(logoutSuccessHandler);

        // 添加Jwt过滤器

        // 禁用csrf防御机制(跨域请求伪造)，这么做在测试和开发会比较方便。
        http.csrf().disable();
    }
}
