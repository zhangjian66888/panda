package com.panda.core.config;

import com.panda.common.security.BCryptPasswordEncoder;
import com.panda.common.security.PasswordEncoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.panda.core.config.AppConfig
 * <p>
 * DATE 2019/9/3
 *
 * @author zhanglijian.
 */
@Configuration
public class AppConfig {

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
