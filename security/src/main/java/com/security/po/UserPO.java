package com.security.po;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import com.security.config.jpa.BaseEntity;
import org.springframework.stereotype.Component;

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
@SQLDelete(sql = "update user set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class UserPO extends BaseEntity {

    @NotNull
    @Column(name = "username", nullable = false, length = 255)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @ManyToMany(targetEntity=RolePO.class)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns={@JoinColumn(name = "role_id")})
    private List<RolePO> rolePOList;

}
