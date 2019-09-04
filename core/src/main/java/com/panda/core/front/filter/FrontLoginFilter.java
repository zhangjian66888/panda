package com.panda.core.front.filter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.panda.common.exception.LoginException;
import com.panda.common.exception.PandaException;
import com.panda.common.exception.PandaFilterException;
import com.panda.core.consts.CoreConst;
import com.panda.core.handler.UserHandler;
import com.panda.core.security.SecurityUser;
import com.panda.core.security.SecurityUserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * com.panda.core.filter.FrontLoginFilter
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
@Slf4j
@Component
@Order(CoreConst.FILTER_ORDER - 3)
public class FrontLoginFilter extends BaseFilter {

    private Cache<String, SecurityUser> userCache = CacheBuilder.newBuilder()
            .expireAfterAccess(1L, TimeUnit.HOURS)
            .build();
    @Autowired
    private UserHandler userHandler;

    public FrontLoginFilter(){
        log.info("==================================FrontLoginFilter===============");
    }

    @Override
    public void doingFilter(HttpServletRequest request, HttpServletResponse response) throws PandaFilterException {
        if (request.getRequestURI().startsWith(CoreConst.FRONT_REQUEST_PREFIX)) {
            String token = request.getHeader("Authorization");
            if (!StringUtils.hasLength(token)) {
                throw new LoginException(HttpServletResponse.SC_UNAUTHORIZED, "token invalid front");
            }
            SecurityUser securityUser = null;
            try {
                securityUser = userCache.get(token, ()->userHandler.frontToken(token));
            } catch (ExecutionException e) {
                throw new PandaException(e);
            }
            SecurityUserContext.setContext(securityUser);
        }
    }
}
