package com.code.po;

import javax.persistence.*;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/10/23 18:08
 */
@Entity
@Table(name = "log")
public class LogPO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "LogPO{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
