package com.test;

import com.code.Application;
import com.code.job.HelloJob;
import com.code.job.TransactionJob;
import com.code.repository.LogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/10/23 18:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class A {

    @Resource
    private LogRepository logRepository;

    @Resource
    TransactionJob transactionJob;

    @Test
    public void t () throws SchedulerException, InterruptedException {
        // 创建一个scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getContext().put("skey", "svalue");

        // 创建一个Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .usingJobData("t1", "tv1")
                .withSchedule(SimpleScheduleBuilder
                        .simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever()
                ).build();
        // 创建一个job
        JobDetail job = JobBuilder.newJob(TransactionJob.class)
                .withIdentity("myjob", "mygroup")
                .build();

        // 注册trigger并启动scheduler
        scheduler.scheduleJob(job, trigger);
        scheduler.start();

        Thread.sleep(100000L);
    }
}
