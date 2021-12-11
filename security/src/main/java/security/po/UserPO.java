package security.po;

import com.sun.istack.NotNull;
import lombok.Data;
import security.config.jpa.BaseEntity;

import javax.persistence.*;
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
public class UserPO extends BaseEntity {

    @NotNull
    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @ManyToMany(targetEntity=RolePO.class)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "id")}, inverseJoinColumns={@JoinColumn(name = "id")})
    private List<RolePO> rolePOList;

}
