package com.code.property;

import com.code.core.JobInformation;
import com.code.job.HelloJob;
import com.code.job.TransactionJob;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 配置系统自启动任务
 * @author msi
 * @version 1.0
 * @date 2021/12/27 20:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "job", ignoreUnknownFields = true)
public class JobProperties {
    /**
     * 存放有效的定时配置
     */
    public static List<JobInformation> jobInformationList = new ArrayList<>();

    /**
     * helloJob
     */
    @NestedConfigurationProperty
    private JobInformation helloJob = new JobInformation(HelloJob.class);

    /**
     * transactionJob
     */
    @NestedConfigurationProperty
    private JobInformation transactionJob = new JobInformation(TransactionJob.class);

}
