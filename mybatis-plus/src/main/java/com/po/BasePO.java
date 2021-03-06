package com.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 类描述：
 * 表的元字段
 * @Author msi
 * @Date 2021-08-14 20:41
 * @Version 1.0
 */
@Data
public class BasePO implements Serializable {
    private static final long serialVersionUID = 8647446252613184267L;
    /**
     * id
     * 防止前端接受精度丢失
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonSerialize(using= ToStringSerializer.class)
    protected Long id;
    /**
     * 是否被删除
     */
    protected Boolean deleted;
    /**
     * 创建数据时的操作用户id
     */
    @TableField(fill = FieldFill.INSERT)
    protected Long createUserId;
    /**
     * 更新数据时的操作用户id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Long updateUserId;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime updateTime;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime createTime;
}
