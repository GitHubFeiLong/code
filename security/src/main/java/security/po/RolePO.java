package security.po;

import com.sun.istack.NotNull;
import lombok.Data;
import security.config.jpa.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/12/11 21:49
 */
@Data
@Entity
@Table(name = "user")
public class RolePO extends BaseEntity {

    @NotNull
    @Column(name = "role_name", nullable = false, length = 255)
    private String roleName;


}


