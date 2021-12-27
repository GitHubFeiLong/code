package com.code.property;

import com.code.config.JobQuartzManager;
import lombok.Data;
import org.quartz.Job;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述：
 *
 * @author msi
 * @version 1.0
 * @date 2021/12/27 20:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "job", ignoreUnknownFields = true)
public class JobProperties {
    /**
     * job集合
     */
    List<JobDetailProperties> jobDetailProperties;
}
