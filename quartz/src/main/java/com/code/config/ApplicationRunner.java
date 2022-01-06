package com.code.config;

import com.code.enumerate.JobMetadataEnum;
import com.code.property.JobProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 类描述：
 *
 * @Author e-Feilong.Chen
 * @Date 2021/12/27 11:40
 */
@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner {

    @Resource
    private JobQuartzManager quartzManager;

    @Resource
    private JobProperties jobProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        quartzManager.start();
        JobProperties.jobInformationList.stream().forEach(p->{
            quartzManager.addJobByJobInformation(p);
        });

        // quartzManager.addJobByJobMetadataEnum(JobMetadataEnum.HELLO_JOB);
    }
}
