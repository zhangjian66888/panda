package com.panda.client.filter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.panda.api.consts.ApiConst;
import com.panda.api.dto.AuthResource;
import com.panda.api.dto.AuthUser;
import com.panda.client.handler.RoleHandler;
import com.panda.client.handler.UserHandler;
import com.panda.common.exception.LoginException;
import com.panda.common.exception.PandaFilterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * com.panda.core.security.LoginFilter
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
@Component
@Order(ApiConst.FILTER_ORDER)
@Slf4j
public class LoginFilter extends AbstractFilter {

    private Cache<String, AuthUser> userCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1L, TimeUnit.HOURS)
            .build();

    @Autowired
    private UserHandler userHandler;

    @Autowired
    private RoleHandler roleHandler;

    public LoginFilter() {
        log.info("==================================LoginFilter===============");
    }

    @Override
    public boolean doingFilter(HttpServletRequest request, HttpServletResponse response) throws PandaFilterException {
        String token = request.getHeader(ApiConst.TOKEN_KEY);
        if (!StringUtils.hasLength(token)) {
            token = Optional.ofNullable(request.getSession().getAttribute(ApiConst.TOKEN_KEY))
                    .map(t -> t.toString()).orElse(null);
        }
        if (!StringUtils.hasLength(token)) {
            throw new LoginException(HttpServletResponse.SC_UNAUTHORIZED, "token invalid");
        }
        AuthUser authUser = null;
        String accessToken = token;
        try {
            authUser = userCache.get(accessToken, () -> userHandler.verifyToken(accessToken));
        } catch (ExecutionException e) {
            throw new LoginException(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }

        List<AuthResource> resources = roleHandler.resourceByRoleIds(authUser.getRoleIds(), authUser.getSuperman());
        authUser.setResources(resources);
        AuthUserContext.setContext(authUser);
        return true;
    }
}
