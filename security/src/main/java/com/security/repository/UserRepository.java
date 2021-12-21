package com.security.repository;

import com.security.po.UserPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * 接口描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/12/21 20:30
 */
public interface UserRepository extends JpaRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO> {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    Optional<UserPO> findByUsername(String username);
}
