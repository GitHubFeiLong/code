package com.code.job;

import com.code.po.LogPO;
import com.code.repository.LogRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/10/23 18:11
 */
@Component
public class TransactionJob implements Job {

    @Resource
    private LogRepository logRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LogPO logPO = new LogPO();
        logPO.setMessage("测试");
        logRepository.save(logPO);
    }
}
