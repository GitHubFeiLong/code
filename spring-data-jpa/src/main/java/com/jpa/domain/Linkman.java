package com.jpa.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * cst_linkman
 * @author 
 */
@Entity
@Table(name = "cst_linkman")
public class Linkman implements Serializable {
    /**
     * 联系人编号(主键)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lkm_id")
    private Long lkmId;

    /**
     * 联系人姓名
     */
    @Column(name = "lkm_name")
    private String lkmName;

    /**
     * 联系人性别
     */
    @Column(name = "lkm_gender")
    private String lkmGender;

    /**
     * 联系人办公电话
     */
    @Column(name = "lkm_phone")
    private String lkmPhone;

    /**
     * 联系人手机
     */
    @Column(name = "lkm_mobile")
    private String lkmMobile;

    /**
     * 联系人邮箱
     */
    @Column(name = "lkm_email")
    private String lkmEmail;

    /**
     * 联系人职位
     */
    @Column(name = "lkm_position")
    private String lkmPosition;

    /**
     * 联系人备注
     */
    @Column(name = "lkm_Memo")
    private String lkmMemo;


    /**
     * 配置联系人到客户的多对一关系
     * 使用注解的形式配置多对一关系
     *  ManyToOne:配置多对一
     *  targetEntity 对方实体类字节码
     * 配置表关系
     * 配置外键的过程，配置多的一方，就会在多的一方维护外键的
     * 配置外键
     */
    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "lkm_cust_id", referencedColumnName = "cust_id")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getLkmId() {
        return lkmId;
    }

    public void setLkmId(Long lkmId) {
        this.lkmId = lkmId;
    }

    public String getLkmName() {
        return lkmName;
    }

    public void setLkmName(String lkmName) {
        this.lkmName = lkmName;
    }

    public String getLkmGender() {
        return lkmGender;
    }

    public void setLkmGender(String lkmGender) {
        this.lkmGender = lkmGender;
    }

    public String getLkmPhone() {
        return lkmPhone;
    }

    public void setLkmPhone(String lkmPhone) {
        this.lkmPhone = lkmPhone;
    }

    public String getLkmMobile() {
        return lkmMobile;
    }

    public void setLkmMobile(String lkmMobile) {
        this.lkmMobile = lkmMobile;
    }

    public String getLkmEmail() {
        return lkmEmail;
    }

    public void setLkmEmail(String lkmEmail) {
        this.lkmEmail = lkmEmail;
    }

    public String getLkmPosition() {
        return lkmPosition;
    }

    public void setLkmPosition(String lkmPosition) {
        this.lkmPosition = lkmPosition;
    }

    public String getLkmMemo() {
        return lkmMemo;
    }

    public void setLkmMemo(String lkmMemo) {
        this.lkmMemo = lkmMemo;
    }



    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "CstLinkman{" +
                "lkmId=" + lkmId +
                ", lkmName='" + lkmName + '\'' +
                ", lkmGender='" + lkmGender + '\'' +
                ", lkmPhone='" + lkmPhone + '\'' +
                ", lkmMobile='" + lkmMobile + '\'' +
                ", lkmEmail='" + lkmEmail + '\'' +
                ", lkmPosition='" + lkmPosition + '\'' +
                ", lkmMemo='" + lkmMemo + '\'' +
                '}';
    }
}