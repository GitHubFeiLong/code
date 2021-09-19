package com.jpa.dao;

import com.jpa.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 接口描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/9/19 20:59
 */
public interface RoleDao extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
}
