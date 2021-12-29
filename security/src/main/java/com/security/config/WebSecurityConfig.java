package com.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.service.MyUserDetailService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

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

    @Resource
    private MyUserDetailService userDetailsService;

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
                // hasRole 参数不能加“ROLE_”前缀，hasAuthority 必须全称
                .antMatchers("/admin/api/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                /// .loginPage("/myLogin.html")
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
                // 增加自动登录，浏览器cookie：name默认是remember-me
                .rememberMe().userDetailsService(userDetailsService)
                .and()
                // 自定义退出登录
                .logout()
                // 指定退出登录接口地址
                .logoutUrl("/logout")
                // 退出成功后的跳转地址
                .logoutSuccessUrl("/")
                // 退出成功的处理器(在addLogoutHandler之后执行)
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        PrintWriter writer = response.getWriter();
                        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                        writer.println("退出成功1");
                    }
                })
                // 让session失效
                .invalidateHttpSession(true)
                // 注销成功，删除cookie
                .deleteCookies("JSESSIONID", "cookie2")
                // 功能和 logoutSuccessHandler 类似
                .addLogoutHandler(new LogoutHandler() {
                    @SneakyThrows
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                        PrintWriter writer = response.getWriter();
                        writer.println("退出成功2");
                    }
                })
                // session管理,默认已经启用migrateSession策略
                .and()
                .sessionManagement()
                // 用于设置单个用户允许同时在线的最大会话数，如果没有额外配置，那么新登录的会话会踢掉旧的会话
                .maximumSessions(1)
                // .sessionFixation()
                /*
                    none：不做任何变动，登录之后沿用旧的session。
                    newSession：登录之后创建一个新的session。
                    migrateSession：登录之后创建一个新的session，并将旧的session中的数据复制过来。
                    changeSessionId：不创建新的会话，而是使用由Servlet容器提供的会话固定保护。
                 */
                // .changeSessionId()
                // session过期跳转url
                // .invalidSessionUrl("/session/invalid")
                // 或者自定义，session过期后的策略
                // .invalidSessionStrategy(new InvalidSessionStrategy() {
                //     @Override
                //     public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                //
                //     }
                // })
                // 阻止新会话登录，默认false，（需要重启才行）
                .maxSessionsPreventsLogin(true)
                // .and()
                // .csrf().disable()
                ;
    }
}
