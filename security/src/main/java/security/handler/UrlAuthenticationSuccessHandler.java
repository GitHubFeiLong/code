package security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import security.config.JwtTokenUtil;
import security.po.UserPO;
import security.repository.UserRepository;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录成功处理器
 * @Author msi
 * @Date 2021-04-02 13:33
 * @Version 1.0
 */
@Slf4j
@Component
public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private UserRepository userRepository;

    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param authentication 保存当前用户的登录信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {

        httpServletResponse.setCharacterEncoding("UTF-8");

        //表单输入的用户名
        String username = (String) authentication.getPrincipal();

        UserPO byUsername = userRepository.findByUsername(username).orElseThrow(()->new RuntimeException(""));

        // 查询用户信息

        String token = JwtTokenUtil.generateBearerToken(byUsername, JwtTokenUtil.VALID_HOUR);
        httpServletResponse.setStatus(200);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        PrintWriter out = httpServletResponse.getWriter();
        // 转成VO
        out.write(new ObjectMapper().writeValueAsString(byUsername));
        // 设置到响应头里
        httpServletResponse.setHeader(JwtTokenUtil.TOKEN_HEADER, token);

        out.flush();
        out.close();
    }
}
