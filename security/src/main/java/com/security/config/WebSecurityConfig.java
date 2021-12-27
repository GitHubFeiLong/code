package com.security.config;

import com.security.service.MyUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 类描述：
 * security 配置
 *
 * @author msi
 * @version 1.0
 * @date 2021/12/21 21:35
 */
@Slf4j
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     auth.userDetailsService(userDetailsService());
    // }

    /**
     * 可以自定义
     * @see MyUserDetailService
     * @return
     */
    // @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // 密码都是123456
        // String hashpw1 = BCrypt.hashpw("123456", BCrypt.gensalt());
        manager.createUser(User.withUsername("user")
                .password("$2a$10$muGb81DxCI761FI7acc1.OB6mSsmF9bf0F7vpu.L3Afejcq3XL.xy")
                .roles("USER").build());
        manager.createUser(User.withUsername("admin")
                .password("$2a$10$1VQt/Or7vTxMSHwNFAFhUeuR7vxWUW.Vr41.Ig09cOEZIj0aKX5ze")
                .roles("USER", "ADMIN").build());

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/myLogin.html")
                // 处理登录请求的接口
                .loginProcessingUrl("/login")
                // 指定登录成功的处理器
                .successHandler(new AuthenticationSuccessHandler() {
                    /**
                     *
                     * @param request
                     * @param response
                     * @param authentication Authentication参数，携带当前登录用户名及其角色等信息
                     * @throws IOException
                     * @throws ServletException
                     */
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        out.write("{\"登录成功:" + authentication.getName() + "\"}");
                    }
                })
                // 登录失败处理器
                .failureHandler(new AuthenticationFailureHandler() {
                    /**
                     *
                     * @param request
                     * @param response
                     * @param exception 异常参数
                     * @throws IOException
                     * @throws ServletException
                     */
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        response.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        out.write("{\"" + exception.getMessage() +"\"}");
                    }
                })
                // 设置登录页可以访问
                .permitAll()
                .and()
                .csrf().disable();
    }
}
