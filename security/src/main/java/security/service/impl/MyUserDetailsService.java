package security.service.impl;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import security.config.MyUserDetails;
import security.po.UserPO;
import security.repository.UserRepository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Andon
 * @date 2019/3/20
 * <p>
 * 自定义用户认证
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    /**
     * 根据用户登录名查询用户信息
     * @param username 用户名/手机号/邮箱
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) {

        MyUserDetails userInfo = new MyUserDetails();
        // 查询用户信息
        UserPO userPO = userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("用户不存在"));

        if (userPO != null) {
            userInfo.setUsername(userPO.getUsername());
            userInfo.setPassword(userPO.getPassword());
        } else {
            throw new BadCredentialsException("账户名与密码不匹配，请重新输入");
        }

        Set<SimpleGrantedAuthority> authoritiesSet = new HashSet<>();
        // 查询用户权限
        List<String> roles = selfAuthorityUserMapper.selectRoleNameByUserId(user.getId());
        for (String roleName : roles) {
            //用户拥有的角色
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authoritiesSet.add(simpleGrantedAuthority);
        }
        // 设置用户的角色
        userInfo.setAuthorities(authoritiesSet);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userInfo;
    }
}
