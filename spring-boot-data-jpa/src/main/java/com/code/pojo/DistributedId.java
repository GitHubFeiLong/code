package com.code.pojo;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author: e-Fang.Liu
 * @description:
 * @date:created in 2021/8/31 17:08
 * @modificed by:
 */
@Slf4j
public class DistributedId implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        if (o == null) throw new HibernateException(new NullPointerException());
        if ((((BaseEntity) o).getId()) == null) {
            long id = IdUtil.getSnowflake(1, 1).nextId();
            return id;
        } else {
            Long id = ((BaseEntity) o).getId();
            return id;
        }
    }
}
