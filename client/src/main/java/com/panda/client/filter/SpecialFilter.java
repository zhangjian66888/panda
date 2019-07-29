package com.panda.client.filter;

import com.panda.api.consts.ApiConst;
import com.panda.client.consts.AuthConst;
import com.panda.common.exception.LoginException;
import com.panda.common.exception.PandaFilterException;
import com.panda.common.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * com.panda.core.security.LoginFilter
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
@Component
@Order(ApiConst.FILTER_ORDER - 1)
@Slf4j
public class SpecialFilter extends AbstractFilter {

    public SpecialFilter() {
        log.info("==================================SpecialFilter===============");
    }

    @Override
    public boolean doingFilter(HttpServletRequest request, HttpServletResponse response) throws PandaFilterException, IOException {
        if (request.getRequestURI()
                .endsWith(PathUtil.urlJoin(AuthConst.AUTH_REQUEST_PREFIX, ApiConst.LOGIN_REQUEST))
                || request.getRequestURI().equalsIgnoreCase(ApiConst.LOGIN_REQUEST)) {
            throw new LoginException(HttpServletResponse.SC_UNAUTHORIZED, "login");
        }
        if (request.getRequestURI()
                .endsWith(PathUtil.urlJoin(AuthConst.AUTH_REQUEST_PREFIX, ApiConst.LOGOUT_REQUEST))
                || request.getRequestURI().equalsIgnoreCase(ApiConst.LOGOUT_REQUEST)) {
            request.getSession().invalidate();
            String homePage = Optional.ofNullable(authProperties.getHomePage()).orElse("/");
            response.sendRedirect(homePage);
            return false;
        }
        if (request.getRequestURI()
                .endsWith(PathUtil.urlJoin(AuthConst.AUTH_REQUEST_PREFIX, ApiConst.SUCCESS_REQUEST_PREFIX))) {
            String token = request.getParameter("token");
            HttpSession session = request.getSession();
            session.setAttribute("Authorization", token);
            session.setMaxInactiveInterval(60 * 60 * 24 * 30);
            String homePage = Optional.ofNullable(authProperties.getHomePage()).orElse("/");
            response.sendRedirect(homePage);
            return false;
        }
        return true;
    }
}
