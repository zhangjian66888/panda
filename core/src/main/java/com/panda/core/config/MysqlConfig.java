package com.panda.core.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.ParsingUtils;

/**
 * com.panda.core.config.MysqlConfig
 * <p>
 * DATE 2019/6/5
 *
 * @author zhanglijian.
 */
@Configuration
public class MysqlConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
