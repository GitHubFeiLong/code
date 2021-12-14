package security.po;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import security.config.jpa.BaseEntity;

import javax.persistence.*;
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
@Table(name = "menu")
@SQLDelete(sql = "update menu set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class MenuPO extends BaseEntity {

    @NotNull
    @Column(name = "menu", nullable = false, length = 255)
    private String menu;

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToMany
    @JoinTable(name="role_menu", joinColumns = {@JoinColumn(name = "role_id")}
            , inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private List<RolePO> rolePOList = new ArrayList<>();
}
