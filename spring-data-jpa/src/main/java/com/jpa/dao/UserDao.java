package com.jpa.dao;

import com.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/9/19 20:58
 */
public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
