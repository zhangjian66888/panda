package com.panda.client.filter;

import com.panda.api.consts.ApiConst;
import com.panda.api.dto.AuthResource;
import com.panda.api.dto.AuthUser;
import com.panda.client.handler.RoleHandler;
import com.panda.common.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * com.panda.core.security.PermissionFilter
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
@Component
@Order(ApiConst.FILTER_ORDER + 1)
@Slf4j
public class PermissionFilter extends AbstractFilter {

    @Autowired
    private RoleHandler roleHandler;

    public PermissionFilter() {
        log.info("==================================PermissionFilter===============");
    }

    @Override
    public boolean doingFilter(HttpServletRequest request, HttpServletResponse response) {
        AuthUser user = AuthUserContext.getContext();
        List<AuthResource> resources = roleHandler.resourceByRoleIds(user.getRoleIds());
        String url;
        String method;
        AntPathRequestMatcher matcher;
        for (AuthResource ga : resources) {
            url = ga.getUrl();
            method = ga.getMethod();
            if (!StringUtils.hasLength(url) || !StringUtils.hasLength(method)) {
                continue;
            }
            matcher = new AntPathRequestMatcher(url);
            if (matcher.matches(request)) {
                //当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利。
                if (method.equalsIgnoreCase(request.getMethod()) || "ALL".equalsIgnoreCase(method)) {
                    return true;
                }
            }
        }
        throw new PermissionException(HttpServletResponse.SC_FORBIDDEN, "inadequate permissions");
    }
}
