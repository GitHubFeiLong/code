package com.code.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: e-Fang.Liu
 * @description:
 * @date:created in 2021/8/31 13:26
 * @modificed by:
 */
@Component
public class MyAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Long id = null;
        if (id == null) {
            id = 8888L;
        }
        return Optional.of(id);
    }
}
