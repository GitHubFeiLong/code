package com.code.property;

import com.code.config.JobQuartzManager;
import lombok.Data;
import org.quartz.Job;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/12/27 20:39
 */
@Data
public class JobDetailProperties {
    /**
     * job key group
     */
    private String jobKeyGroup = JobQuartzManager.DEFAULT_JOB_KEY_GROUP;

    /**
     * trigger key group
     */
    private String triggerKeyGroup = JobQuartzManager.DEFAULT_TRIGGER_KEY_GROUP;

    /**
     * 任务名
     */
    private String jobName;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 执行任务的实例类对象
     */
    private Class<? extends Job> jobClass;
}
