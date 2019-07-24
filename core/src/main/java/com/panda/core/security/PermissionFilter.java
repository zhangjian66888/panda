package com.panda.core.security;

import com.panda.common.exception.PermissionException;
import com.panda.core.consts.CoreConst;
import com.panda.core.handler.RoleHandler;
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
@Order(CoreConst.FILTER_ORDER + 1)
@Slf4j
public class PermissionFilter extends AbstractFilter {

    @Autowired
    private RoleHandler roleHandler;

    public PermissionFilter() {
        log.info("==================================PermissionFilter===============");
    }

    @Override
    public void doingFilter(HttpServletRequest request, HttpServletResponse response) {
        SecurityUser user = SecurityUserContext.getContext();
        List<GrantedAuthority> authorities = roleHandler.authoritiesByRoleIds(user.getRoleIds());
        String url;
        String method;
        AntPathRequestMatcher matcher;
        for (GrantedAuthority ga : authorities) {
            url = ga.getUrl();
            method = ga.getMethod();
            if (!StringUtils.hasLength(url) || !StringUtils.hasLength(method)) {
                continue;
            }
            matcher = new AntPathRequestMatcher(url);
            if (matcher.matches(request)) {
                //当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利。
                if (method.equalsIgnoreCase(request.getMethod()) || "ALL".equalsIgnoreCase(method)) {
                    return;
                }
            }
        }
        throw new PermissionException(HttpServletResponse.SC_FORBIDDEN,"inadequate permissions");
    }
}
