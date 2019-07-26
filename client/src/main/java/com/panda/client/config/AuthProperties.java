package com.panda.client.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * com.panda.client.config.PandaProperties
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "panda.auth")
public class AuthProperties {

    private Long appCode;

    private String appSecret;

    private String backHost;

    private String frontHost;

    private String level;

    private String homePage;

    @Autowired
    private Environment environment;

    public String profile(){
        return Arrays.stream(environment.getActiveProfiles()).collect(Collectors.joining(","));
    }
}
