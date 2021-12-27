package com.security.po;

import com.security.config.BasePO;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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
public class UserPO extends BasePO implements UserDetails {

    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(targetEntity=RolePO.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns={@JoinColumn(name = "role_id")})
    private List<RolePO> rolePOList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        rolePOList.forEach(p->{
            authorities.add(new SimpleGrantedAuthority(p.getRoleName()));
        });
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
