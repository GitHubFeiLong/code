package com.code.test;

import com.code.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 类描述：
 * 使用
 * @Author e-Feilong.Chen
 * @Date 2021/10/21 8:42
 */
public class Test {
    public static void main(String[] args) throws SchedulerException {
        // 创建一个scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getContext().put("skey", "svalue");

        // 创建一个Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .usingJobData("t1", "tv1")
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(3)
                        .repeatForever()
                ).build();
        // 创建一个job
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .usingJobData("j1", "jv1")
                .withIdentity("myjob", "mygroup")
                .build();
        job.getJobDataMap().put("jv2", "jv2");

        // 注册trigger并启动scheduler
        scheduler.scheduleJob(job, trigger);
        scheduler.start();

    }
}
