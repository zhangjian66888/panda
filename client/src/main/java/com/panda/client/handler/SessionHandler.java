package com.panda.client.handler;

import com.panda.client.config.SessionRedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * com.panda.client.handler.SessionHandler
 * <p>
 * DATE 2019/7/29
 *
 * @author zhanglijian.
 */
@Component
@Slf4j
@ConditionalOnExpression("${panda.session.redis.enable:false}")
public class SessionHandler {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SessionRedisProperties sessionRedisProperties;

    public void saveSession(String sessionId, String token) {
        if (StringUtils.hasLength(sessionId) && StringUtils.hasLength(token)) {
            redisTemplate.opsForValue().set(sessionId, token, sessionRedisProperties.getTimeout(), TimeUnit.SECONDS);
        }
    }

    public String sessioIdToToken(String sessionId) {
        return redisTemplate.opsForValue().get(sessionId);
    }

}
