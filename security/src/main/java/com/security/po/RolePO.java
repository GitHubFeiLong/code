package com.security.po;

import com.security.config.BasePO;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
@Table(name = "role")
@SQLDelete(sql = "update role set deleted = true where id = ?")
@Where(clause = "deleted = false")
public class RolePO extends BasePO {

    @NotNull
    @Column(name = "role_name", nullable = false)
    private String roleName;

    @ManyToMany(targetEntity=UserPO.class)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns={@JoinColumn(name = "user_id")})
    private List<UserPO> userPOList = new ArrayList<>();

}
