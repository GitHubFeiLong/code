package com.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 类描述：
 *
 * @Author msi
 * @Date 2021-08-14 14:17
 * @Version 1.0
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private final String createTimeField = "createTime";
    private final String createUserIdField = "createUserId";
    private final String updateTimeField = "updateTime";
    private final String updateUserIdField = "updateUserId";

    /**
     * 执行insert时填充数据
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(createTimeField, metaObject);
        Object createUserId = getFieldValByName(createUserIdField, metaObject);
        Object updateTime = getFieldValByName(updateTimeField, metaObject);
        Object updateUserId = getFieldValByName(updateUserIdField, metaObject);

        if(createTime == null) {
            //字段为空，可以进行填充
            setFieldValByName(createTimeField, LocalDateTime.now(), metaObject);
        }
        if (createUserId == null) {
            //字段为空，可以进行填充
            setFieldValByName(createUserIdField, 1L, metaObject);
        }
        if(updateTime == null) {
            //字段为空，可以进行填充
            setFieldValByName(updateTimeField, LocalDateTime.now(), metaObject);
        }
        if (updateUserId == null) {
            //字段为空，可以进行填充
            setFieldValByName(updateUserIdField, 1L, metaObject);
        }
    }

    /**
     * 执行update时填充数据
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName(updateTimeField, metaObject);
        Object updateUserId = getFieldValByName(updateUserIdField, metaObject);
        //字段为空，可以进行填充
        setFieldValByName(updateTimeField, LocalDateTime.now(), metaObject);
        //字段为空，可以进行填充
        setFieldValByName(updateUserIdField, 1L, metaObject);
    }
}
