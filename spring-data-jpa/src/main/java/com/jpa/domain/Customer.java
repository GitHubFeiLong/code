package com.jpa.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * cst_customer
 * 客户的实体类
 * 配置映射关系
 * 1.实体类和表的映射关系
 *      @Entity: 申明实体类
 *      @Table：配置实体类和表的映射关系
 *          name属性：表名称
 * 2.实体类中属性和表中字段的映射关系
 *      @Id：申明主键的配置
 *      @GeneratedValue: 配置主键的生成策略
 *          strategy = GenerationType.IDENTITY：自增
 *
 * @author 
 */
@Entity
@Table(name = "cst_customer")
public class Customer implements Serializable {
    /**
     * 客户编号(主键)
     * @Id：申明主键的配置
     * @GeneratedValue: 配置主键的生成策略
     *      strategy
     *          GenerationType.IDENTITY：自增
     *              * 底层数据库必须支持自动增长
     *          GenerationType.SEQUENCE: 序列
     *              * 底层数据库必须支持序列
     *          GenerationType.TABLE： jpa提供的一种机制，通过一张数据库表的形式帮助我们完成自增
     *          GenerationType.AUTO：程序自动的帮助我们选择主键生成策略
     * @Column:配置属性和字段的映射关系
     *      name:数据库表列的字段名称
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long custId;

    /**
     * 客户名称(公司名称)
     */
    @Column(name = "cust_name")
    private String custName;

    /**
     * 客户信息来源
     */
    @Column(name = "cust_source")
    private String custSource;

    /**
     * 客户所属行业
     */
    @Column(name = "cust_industry")
    private String custIndustry;

    /**
     * 客户级别
     */
    @Column(name = "cust_level")
    private String custLevel;

    /**
     * 客户联系地址
     */
    @Column(name = "cust_address")
    private String custAddress;

    /**
     * 客户联系电话
     */
    @Column(name = "cust_phone")
    private String custPhone;

    private static final long serialVersionUID = 1L;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustSource() {
        return custSource;
    }

    public void setCustSource(String custSource) {
        this.custSource = custSource;
    }

    public String getCustIndustry() {
        return custIndustry;
    }

    public void setCustIndustry(String custIndustry) {
        this.custIndustry = custIndustry;
    }

    public String getCustLevel() {
        return custLevel;
    }

    public void setCustLevel(String custLevel) {
        this.custLevel = custLevel;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custId=" + custId +
                ", custName='" + custName + '\'' +
                ", custSource='" + custSource + '\'' +
                ", custIndustry='" + custIndustry + '\'' +
                ", custLevel='" + custLevel + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custPhone='" + custPhone + '\'' +
                '}';
    }
}