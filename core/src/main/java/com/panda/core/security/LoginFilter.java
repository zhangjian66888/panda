package com.panda.core.security;

import com.panda.common.exception.LoginException;
import com.panda.common.exception.PandaFilterException;
import com.panda.core.consts.CoreConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * com.panda.core.security.LoginFilter
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
@Component
@Order(CoreConst.FILTER_ORDER)
@Slf4j
public class LoginFilter extends AbstractFilter {

    @Autowired
    private SecurityUserHandler securityUserHandler;

    public LoginFilter() {
        log.info("==================================LoginFilter===============");
    }

    @Override
    public void doingFilter(HttpServletRequest request, HttpServletResponse response) throws PandaFilterException {
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasLength(token)) {
            throw new LoginException(HttpServletResponse.SC_UNAUTHORIZED, "token invalid");
        }
        SecurityUser securityUser = securityUserHandler.verifyToken(token);
        SecurityUserContext.setContext(securityUser);
    }
}
