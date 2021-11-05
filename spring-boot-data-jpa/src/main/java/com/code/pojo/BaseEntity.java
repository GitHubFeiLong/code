package com.code.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author: e-Fang.Liu
 * @description: 基础实体-泛型必传
 * @date:created in 2021/8/31 13:18
 * @modificed by:
 */
@Data
@SuppressWarnings("unchecked")
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 7735356182172011781L;

    private static final String ID_GEN = "com.code.pojo.DistributedId";

    @Id
    @GenericGenerator(name = "IdGen", strategy = ID_GEN)
    @GeneratedValue(generator = "IdGen")
    protected Long id;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    /**
     * 创建人id
     */
    @CreatedBy
    @Column(name = "create_user_id")
    private Long createUserId;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    /**
     * 更新人id
     */
    @LastModifiedBy
    @Column(name = "update_user_id")
    private Long updateUserId;

    /**
     * 删除状态 0 正常1 删除
     */
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;
}
