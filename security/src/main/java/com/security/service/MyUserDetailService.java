package com.security.service;

import com.security.dto.UserDetailsDTO;
import com.security.po.UserPO;
import com.security.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 类描述：
 * 加载用户
 * @author msi
 * @version 1.0
 * @date 2021/12/21 20:37
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserPO user = userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUsername(user.getUsername());
        userDetailsDTO.setPassword(user.getPassword());

        // 角色
        Set<SimpleGrantedAuthority> authoritiesSet = new HashSet<>();
        user.getRolePOList().forEach(p->{
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(p.getRoleName());
            authoritiesSet.add(simpleGrantedAuthority);
        });
        userDetailsDTO.setAuthorities(authoritiesSet);

        return userDetailsDTO;
    }
}
