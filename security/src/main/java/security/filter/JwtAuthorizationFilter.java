package security.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import security.config.JwtTokenUtil;
import security.po.RolePO;
import security.po.UserPO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类描述：
 * 过滤器，用户请求时，获取请求头的token，并将其解析后设置到Authentication认证信息中去
 * 使用JWT token进行验证用户
 * @Author msi
 * @Date 2021-04-03 18:14
 * @Version 1.0
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {


        String tokenHeader = request.getHeader("Authorization");

        // 如果请求头中没有Authorization信息则直接放行了
        boolean boo = tokenHeader == null
                || !(tokenHeader.startsWith("Bearer ") || tokenHeader.startsWith("Basic "));
        if (boo) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 这里从token中获取用户信息，并将用户名和角色信息，创建一个认证对象 Authentication
     * @param tokenHeader token
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) throws UnsupportedEncodingException, JsonProcessingException {
        // 判断请求头是Bearer 还是 Basic 开头
        if (tokenHeader.startsWith("Bearer ")) {
            return getBearerAuthenticationToken(tokenHeader);
        }
        if (tokenHeader.startsWith("Basic ")) {
            return getBasicAuthenticationToken(tokenHeader);
        }

        throw new RuntimeException("格式错误");
    }

    /**
     * Basic方式 解码 base64字符串
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    private UsernamePasswordAuthenticationToken getBasicAuthenticationToken(String token) throws UnsupportedEncodingException {
        // 将加工后的转换原生token（没有Bearer ，Basic 字符串）
        String base64 = token.replace("Basic ", "");
        // 解码
        String decode = new String(Base64.getDecoder().decode(base64), "UTF-8");
        // base64解码后字符串是正确的格式
        boolean isSureFormat = decode != null && decode.split(":").length == 2;
        // 正确格式，查询用户和设置权限。
        if (isSureFormat) {
            // arr[0] 用户名； arr[1] 密码
            String[] arr = decode.split(":");

            // 查询用户
            UserPO userPO = null;
            // AuthorityUserDTO authorityUserDTO = selfAuthorityUserMapper.selectUserDetailByUsername(arr[0]);
            // 用户不存在
            if (userPO == null) {
                throw new RuntimeException("用户不存在");
            }
            // 使用 BCrypt 加密的方式进行匹配
            boolean matches = new BCryptPasswordEncoder().matches(arr[1], userPO.getPassword());
            // 密码不正确，抛出异常
            if (!matches) {
                throw new RuntimeException("密码错误");
            }

            // 放置权限
            Set<SimpleGrantedAuthority> authoritiesSet = new HashSet<>();

            if (!userPO.getRolePOList().isEmpty()) {
                userPO.getRolePOList().stream().forEach(f1->{
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(f1.getRoleName());
                    authoritiesSet.add(simpleGrantedAuthority);
                });
            }
            String username = userPO.getUsername();
            if (username != null){
                // 用户名 密码 角色
                return new UsernamePasswordAuthenticationToken(username, null, authoritiesSet);
            }
        }

        throw new RuntimeException("请求头格式错误");
    }

    /**
     * Bearer 方式
     * @param token
     * @return
     */
    private UsernamePasswordAuthenticationToken getBearerAuthenticationToken(String token) throws JsonProcessingException {
        // 解析token为对象
        UserPO userPO = JwtTokenUtil.resolveToken(token);

        // 放置权限
        Set<SimpleGrantedAuthority> authoritiesSet = new HashSet<>();
        List<RolePO> rolePOList = userPO.getRolePOList();
        if (rolePOList != null && !rolePOList.isEmpty()) {
            rolePOList.parallelStream().forEach(f1->{
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(f1.getRoleName());
                authoritiesSet.add(simpleGrantedAuthority);
            });

        }
        String username = userPO.getUsername();
        if (username != null){
            // 用户名 密码 角色
            return new UsernamePasswordAuthenticationToken(username, null, authoritiesSet);
        }
        return null;
    }
}
