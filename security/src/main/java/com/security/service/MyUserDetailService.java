package com.security.service;

import com.security.po.UserPO;
import com.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 类描述：
 * 加载用户
 * @author msi
 * @version 1.0
 * @date 2021/12/21 20:37
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserPO user = userRepository.findByUsername(s).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        return user;
    }
}
