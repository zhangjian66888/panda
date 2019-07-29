package com.panda.client.filter;

import com.panda.api.consts.ApiConst;
import com.panda.client.handler.SessionHandler;
import com.panda.common.exception.PandaFilterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * com.panda.client.filter.SessionRedisFilter
 * <p>
 * DATE 2019/7/29
 *
 * @author zhanglijian.
 */
@Component
@Order(ApiConst.FILTER_ORDER - 2)
@Slf4j
@ConditionalOnExpression("${panda.session.redis.enable:false}")
public class SessionRedisFilter extends AbstractFilter {

    @Autowired
    private SessionHandler sessionHandler;

    @Override
    public boolean doingFilter(HttpServletRequest request, HttpServletResponse response)
            throws PandaFilterException, IOException {

        String token = Optional.ofNullable(request.getSession().getAttribute(ApiConst.TOKEN_KEY))
                .map(t -> t.toString()).orElse(null);
        if (!StringUtils.hasLength(token)) {
            String sessionId = Arrays.stream(request.getCookies()).filter(t -> t.getName()
                    .equalsIgnoreCase("JSESSIONID"))
                    .findFirst().map(t -> t.getValue()).orElse(null);
            if (StringUtils.hasLength(sessionId)) {
               token = sessionHandler.sessioIdToToken(sessionId);
               if (StringUtils.hasLength(token)){
                   request.getSession().setAttribute(ApiConst.TOKEN_KEY, token);
               }
            }
        }
        return true;
    }
}
