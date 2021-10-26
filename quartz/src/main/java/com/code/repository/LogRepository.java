package com.code.repository;

import com.code.po.LogPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/10/23 18:21
 */
@Repository
public interface LogRepository extends JpaRepository<LogPO, Long>, JpaSpecificationExecutor<LogPO> {
}
