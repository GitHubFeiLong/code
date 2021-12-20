package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.security.po.MenuPO;

import java.util.Optional;

/**
 * 接口描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/12/12 12:33
 */
public interface MenuRepository extends JpaRepository<MenuPO, Long>, JpaSpecificationExecutor<MenuPO> {

    Optional<MenuPO> findByMenu(String menu);
}
