package com.code.repository;

import com.code.domain.UserPO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 接口描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/11/7 9:16
 */
@Repository
public interface UserRepository extends CrudRepository<UserPO, Long>, JpaSpecificationExecutor<UserPO> {
}
