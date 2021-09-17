package com.jpa.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * 类描述：
 * 解决实体管理器工厂的良妃资源和耗时间的问题
 * 通过静态代码块的的方式，当程序第一次访问此工具类时，创建一个公共的实体管理器工厂对象
 * 第一次访问 getEntityManager方法，经过静态代码块创建一个factory对象，再调用方法创建一个EntityManager对象
 * 第二次访问 getEntityManager 方法，直接通过一个已经创建好的 factory对象，再创建EntityManager对象
 * @Author e-Feilong.Chen
 * @Date 2021/9/17 10:54
 */
public class JpaUtils {
    private static EntityManagerFactory factory;
    static {
        // 1. 加载配置文件，创建 EntityManagerFactory
        factory = Persistence.createEntityManagerFactory("myJpa");
    }

    /**
     * 获取EntityManager对象
     * @return
     */
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
