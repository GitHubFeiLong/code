package com.code.autoconfigure;

import cn.hutool.core.bean.BeanUtil;
import com.code.core.JobInformation;
import com.code.property.JobProperties;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 类描述：
 * 任务自动配置
 * @Author e-Feilong.Chen
 * @Date 2022/1/6 17:02
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(JobProperties.class)
public class JobAutoConfiguration{

    private final JobProperties jobProperties;

    public JobAutoConfiguration(JobProperties jobProperties) {
        this.jobProperties = jobProperties;
    }

    /**
     * 校验参数
     * @return
     */
    @Bean
    public JobProperties jobProperties() {
        List<JobInformation> jobInformationList = JobProperties.jobInformationList;

        Stream.of(jobProperties.getClass().getDeclaredFields())
                .filter(f-> Objects.equals(f.getType().getName(), JobInformation.class.getName()))
                .map(Field::getName)
                .forEach(f->{
                    JobInformation jobInformation = BeanUtil.getProperty(jobProperties, f);
                    // 配置了名称和cron表达式才有效
                    if (jobInformation.getJobName() != null && jobInformation.getCronExpression() != null) {
                        String cronExpression = jobInformation.getCronExpression();
                        if (!CronExpression.isValidExpression(cronExpression)) {
                            log.error("定时器的表达式无效：{}", cronExpression);
                            throw new BeanCreationException("定时器的表达式无效");
                        }
                        jobInformationList.add(jobInformation);
                    }
                });

        // 重复是否需要
        List<String> jobStringList = jobInformationList.stream()
                .map(m -> m.getJobKeyGroup() + m.getJobName())
                .collect(Collectors.toList());
        HashSet<String> jobStringSet = new HashSet<>(jobStringList);
        if (jobStringSet.size() != jobStringList.size()) {
            throw new BeanCreationException("配置的定时任务，组和任务名重复，创建任务报错");
        }

        return jobProperties;
    }
}
