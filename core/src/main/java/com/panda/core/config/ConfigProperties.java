package com.panda.core.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * com.panda.core.config.VariableConfig
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigProperties {

    @Value("${app.code}")
    private Long appCode;

    @Value("${default.passwd}")
    private String defaultPasswd;
}
