package com.code.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import java.time.LocalDateTime;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/10/21 8:36
 */
public class HelloJob implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Object tv1 = jobExecutionContext.getTrigger().getJobDataMap().get("t1");
        Object tv2 = jobExecutionContext.getTrigger().getJobDataMap().get("t2");
        Object jv1 = jobExecutionContext.getJobDetail().getJobDataMap().get("j1");
        Object jv2 = jobExecutionContext.getJobDetail().getJobDataMap().get("j2");

        Object sv = null;
        try {
            sv = jobExecutionContext.getScheduler().getContext().get("skey");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        System.out.println("tv1 = " + tv1);
        System.out.println("jv1 = " + jv1);
        System.out.println("sv = " + sv);
        System.out.println("hello: " + LocalDateTime.now());
    }
}
