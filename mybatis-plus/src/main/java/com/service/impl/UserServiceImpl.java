package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mapper.UserMapper;
import com.po.UserPO;
import com.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/12/11 15:32
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
}
