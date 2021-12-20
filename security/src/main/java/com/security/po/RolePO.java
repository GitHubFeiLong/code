package com.security.po;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import com.security.config.jpa.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/12/11 21:49
 */
@Data
@Entity
@Table(name = "role")
@SQLDelete(sql = "update role set deleted=true where id=?")
@Where(clause = "deleted = false")
public class RolePO extends BaseEntity {

    @NotNull
    @Column(name = "role_name", nullable = false, length = 255)
    private String roleName;

    /**
     * 角色被用的用户
     */
    @ManyToMany
    @JoinTable(name = "user_role_t",
            joinColumns = {@JoinColumn(name="role_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")}
    )
    private Set<UserPO> users = new HashSet<>();

    /**
     * 角色包含的菜单
     */
    @ManyToMany
    @JoinTable(name = "role_menu",
            joinColumns = {@JoinColumn(name="role_id")},
            inverseJoinColumns = {@JoinColumn(name="menu_id")}
    )
    private Set<MenuPO> menus = new HashSet<>();
}


