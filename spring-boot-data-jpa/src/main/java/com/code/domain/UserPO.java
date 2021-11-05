package com.code.domain;

import com.code.pojo.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/11/5 15:47
 */
@Getter
@Setter
@Entity
@Table(name = "user")
public class UserPO extends BaseEntity<UserPO> {

    @Column(name = "name", nullable = false)
    private String name;
}
