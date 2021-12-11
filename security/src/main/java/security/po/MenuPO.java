package security.po;

import com.sun.istack.NotNull;
import lombok.Data;
import security.config.jpa.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
public class MenuPO extends BaseEntity {

    @NotNull
    @Column(name = "menu", nullable = false, length = 255)
    private String menu;

    @Column(name = "parent_id")
    private Long parentId;
}
