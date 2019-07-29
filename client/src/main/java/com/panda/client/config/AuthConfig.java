package com.panda.client.config;

import com.panda.client.clients.AuthClient;
import com.panda.client.clients.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * com.panda.client.config.AuthConfig
 * <p>
 * DATE 2019/7/25
 *
 * @author zhanglijian.
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.panda.client"})
public class AuthConfig {

    public AuthConfig() {
        log.info("panda client config init");
    }

    @Autowired
    private AuthProperties authProperties;

    @Bean
    public ACLProperties aclProperties() {
        return new ACLProperties();
    }

    @Bean
    public AuthProperties authProperties() {
        return new AuthProperties();
    }

    @Bean
    public SessionRedisProperties sessionRedisProperties() {
        return new SessionRedisProperties();
    }

    @Bean
    public AuthClient authClient() {
        return Client.createDefalut(AuthClient.class, authProperties.getBackHost());
    }

    /*@Bean
    public FilterRegistrationBean<LoginFilter> loginFiter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new LoginFilter());
        filterRegistrationBean.setOrder(ApiConst.FILTER_ORDER);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<PermissionFilter> permissionFiter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new PermissionFilter());
        filterRegistrationBean.setOrder(ApiConst.FILTER_ORDER + 1);
        return filterRegistrationBean;
    }*/
}
