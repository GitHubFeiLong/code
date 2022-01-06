package com.code.job;

import org.quartz.*;

import java.time.LocalDateTime;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/10/21 8:36
 */
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class HelloJob implements Job {
    public static int count = 0;
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("HELLO JOB : " + HelloJob.count++);
        // Object tv1 = jobExecutionContext.getTrigger().getJobDataMap().get("t1");
        // Object tv2 = jobExecutionContext.getTrigger().getJobDataMap().get("t2");
        // Object jv1 = jobExecutionContext.getJobDetail().getJobDataMap().get("j1");
        // Object jv2 = jobExecutionContext.getJobDetail().getJobDataMap().get("j2");
        //
        // Object sv = null;
        // try {
        //     sv = jobExecutionContext.getScheduler().getContext().get("skey");
        // } catch (SchedulerException e) {
        //     e.printStackTrace();
        // }
        // jobExecutionContext.getTrigger().getJobDataMap().put("t1", "tvvv1");
        // System.out.println("tv1 = " + tv1);
        // System.out.println("jv1 = " + jv1);
        // System.out.println("sv = " + sv);
        // System.out.println("hello: " + LocalDateTime.now());
        // try {
        //     Thread.sleep(2000L);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
    }
}
