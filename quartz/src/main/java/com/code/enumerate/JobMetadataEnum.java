package com.code.enumerate;

import com.code.config.JobQuartzManager;
import com.code.job.HelloJob;
import com.code.job.TransactionJob;
import com.code.property.JobProperties;
import lombok.Getter;
import org.quartz.Job;

/**
 * 枚举描述：
 * 任务的元数据枚举
 *
 * @deprecated 硬编码，不灵活。
 * @see JobProperties
 * @Author e-Feilong.Chen
 * @Date 2021/12/27 14:29
 */
@Deprecated
@Getter
public enum JobMetadataEnum {

    /**
     * HelloJob
     */
    HELLO_JOB("hello_job", "0/5 * * * * ? *", HelloJob.class),


    TRANSACTION_JOB("transaction_job", "0 0/30 * * * ? *", TransactionJob.class),
    ;

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

    /**
     * 使用默认的组
     * @param jobName
     * @param cronExpression
     * @param jobClass
     */
    JobMetadataEnum(String jobName, String cronExpression, Class<? extends Job> jobClass) {
        this.jobName = jobName;
        this.cronExpression = cronExpression;
        this.jobClass = jobClass;
    }

    /**
     * 自定义组名
     * @param jobKeyGroup
     * @param triggerKeyGroup
     * @param jobName
     * @param cronExpression
     * @param jobClass
     */
    JobMetadataEnum(String jobKeyGroup, String triggerKeyGroup, String jobName, String cronExpression, Class<? extends Job> jobClass) {
        this.jobKeyGroup = jobKeyGroup;
        this.triggerKeyGroup = triggerKeyGroup;
        this.jobName = jobName;
        this.cronExpression = cronExpression;
        this.jobClass = jobClass;
    }
}
