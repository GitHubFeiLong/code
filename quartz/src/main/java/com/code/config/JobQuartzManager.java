package com.code.config;

import com.code.enumerate.JobMetadataEnum;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/12/27 10:03
 */
@Slf4j
@Component
@Scope("singleton")
public class JobQuartzManager implements ApplicationContextAware {

    /**
     * 调度器 工厂
     */
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    /**
     * 默认job的组名
     */
    public static final String DEFAULT_JOB_KEY_GROUP = "default_job_key_group";

    /**
     * 默认trigger的组名
     */
    public static final String DEFAULT_TRIGGER_KEY_GROUP = "default_trigger_key_group";

    /**
     * 任务工厂
     */
    @Resource
    private JobFactory jobFactory;

    /**
     * 调度器
     */
    private Scheduler scheduler;

    /**
     * 应用上下问
     */
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 初始化定时任务
     * @throws SchedulerException
     */
    public void start() throws SchedulerException {
        this.scheduler = schedulerFactory.getScheduler();
        scheduler.setJobFactory(jobFactory);
    }

    /**
     * 添加定时任务
     * @param jobMetadataEnum 定时任务元数据
     * @return 添加成功返回true
     */
    public boolean addJobByJobMetadataEnum(JobMetadataEnum jobMetadataEnum) {
        String cronExp = jobMetadataEnum.getCronExpression();

        if (!CronExpression.isValidExpression(cronExp)) {
            log.error("Illegal cron expression format({})", cronExp);
            return false;
        }
        String jobName = jobMetadataEnum.getJobName();
        Class jobClass = jobMetadataEnum.getJobClass();
        try {
            // 任务
            JobDetail jobDetail = JobBuilder.newJob().withIdentity(new JobKey(jobName, jobMetadataEnum.getJobKeyGroup()))
                    .ofType((Class<Job>) Class.forName(jobClass.getName()))
                    .build();

            // 触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                    .withIdentity(new TriggerKey(jobName, jobMetadataEnum.getTriggerKeyGroup()))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("QuartzManager add job failed");
        }

        return false;
    }

    /**
     * 更新定时任务
     * @param jobMetadataEnum 定时任务元数据
     * @return 添加成功返回true
     */
    public boolean updateJobByJobMetadataEnum(JobMetadataEnum jobMetadataEnum) throws SchedulerException {
        String cronExp = jobMetadataEnum.getCronExpression();

        if (!CronExpression.isValidExpression(cronExp)) {
            log.error("Illegal cron expression format({})", cronExp);
            return false;
        }
        String jobName = jobMetadataEnum.getJobName();
        JobKey jobKey = new JobKey(jobName, jobMetadataEnum.getJobKeyGroup());
        TriggerKey triggerKey = new TriggerKey(jobName, jobMetadataEnum.getTriggerKeyGroup());

        // 检查jobKey和triggerKey是否存在。
        if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
            // 获取定时任务详情
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            // 重新创建一个触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExp))
                    .withIdentity(triggerKey)
                    .build();

            // 更新
            scheduler.rescheduleJob(triggerKey, trigger);

            return true;
        }

        return false;
    }

    /**
     * 根据jobMetadataEnum删除定时任务
     * @param jobMetadataEnum 定时任务元数据
     * @return 添加成功返回true
     */
    public boolean deleteJobByJobMetadataEnum(JobMetadataEnum jobMetadataEnum) throws SchedulerException {
        JobKey jobKey = new JobKey(jobMetadataEnum.getJobName(), jobMetadataEnum.getJobKeyGroup());

       return deleteJobByJobKey(jobKey);
    }

    /**
     * 根据jobKey对象删除任务
     * @param jobKey
     * @return
     */
    public boolean deleteJobByJobKey(JobKey jobKey) {
        try {
            if (scheduler.checkExists(jobKey)) {
                return scheduler.deleteJob(jobKey);
            }

            log.warn("delete job name:{},group name:{} not exists.", jobKey.getName(), jobKey.getGroup());
        } catch (SchedulerException e) {
            log.error("delete job name:{},group name:{} not exists.", jobKey.getName(), jobKey.getGroup());
            e.printStackTrace();
        }

        return false;
    }
}
