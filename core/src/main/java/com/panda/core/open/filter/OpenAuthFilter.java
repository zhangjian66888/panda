package com.panda.core.open.filter;

import com.panda.api.consts.ApiConst;
import com.panda.common.exception.AuthException;
import com.panda.common.exception.PandaFilterException;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaAppSecretDto;
import com.panda.core.service.IPandaAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

/**
 * com.panda.core.interceptor.OpenAuthInterceptor
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
@Slf4j
@Component
@Order(CoreConst.FILTER_ORDER - 2)
public class OpenAuthFilter extends BaseFilter {

    @Autowired
    protected IPandaAppService iPandaAppService;

    public OpenAuthFilter() {
        log.info("==================================OpenAuthFilter===============");
    }

    @Override
    public void doingFilter(HttpServletRequest request, HttpServletResponse response) throws PandaFilterException {
        if (request.getRequestURI().startsWith(ApiConst.API_REQUEST_PREFIX)) {
            Long appCode = Optional.ofNullable(request.getHeader("appCode")).map(t -> Long.valueOf(t)).orElse(null);
            String secret = request.getHeader("secret");
            String profile = request.getHeader("profile");
            if (Objects.isNull(appCode) || !StringUtils.hasLength(secret) || !StringUtils.hasLength(profile)) {
                throw new AuthException(HttpServletResponse.SC_UNAUTHORIZED, "应用认证参数缺失");
            }
            PandaAppSecretDto secretDto = iPandaAppService.secret(appCode, secret, profile.split(","));
            if (Objects.isNull(secretDto)) {
                throw new AuthException(HttpServletResponse.SC_UNAUTHORIZED, "应用认证失败");
            }
        }
    }
}
