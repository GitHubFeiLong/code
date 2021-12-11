package security.config.jpa;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * 类描述：
 *
 * 设置当前用户的id
 * @author msi
 * @date 2021/12/11 20:41
 * @version 1.0
 */
public class MyAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Long id = null;
        if (id == null) {
            id = 0L;
        }
        return Optional.of(id);
    }
}
