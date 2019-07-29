package com.panda.client.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
@ConfigurationProperties(prefix = "panda.session.redis")
public class SessionRedisProperties {

    private Boolean enable;

    private Long timeout = 60 * 60 * 24 * 30L;
}
