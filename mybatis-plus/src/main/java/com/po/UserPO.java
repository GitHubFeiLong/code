package com.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/12/11 15:13
 */
@Data
@TableName("user")
public class UserPO extends BasePO{

    /**
     * 用户名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 地址
     */
    private String address;

    @Override
    public String toString() {
        return "UserPO{" +
                "id=" + id +
                ", deleted=" + deleted +
                ", createUserId=" + createUserId +
                ", updateUserId=" + updateUserId +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                '}';
    }
}
