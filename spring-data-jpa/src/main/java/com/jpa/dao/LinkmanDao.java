package com.jpa.dao;

import com.jpa.domain.Linkman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 接口描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/9/19 9:09
 */
public interface LinkmanDao extends JpaRepository<Linkman, Long>, JpaSpecificationExecutor<Linkman> {
}
