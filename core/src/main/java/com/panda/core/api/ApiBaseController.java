package com.panda.core.api;

import com.panda.common.exception.AuthException;
import com.panda.core.dto.PandaAppSecretDto;
import com.panda.core.service.IPandaAppService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * com.panda.core.api.ApiBaseController
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */
public class ApiBaseController {

    @Autowired
    protected IPandaAppService iPandaAppService;

    protected void verifyApp(Long appCode, String secret, String profile) {
        PandaAppSecretDto secretDto = iPandaAppService.secret(appCode, secret, profiles(profile));
        if (Objects.isNull(secretDto)) {
            throw new AuthException("应用认证失败");
        }
    }

    protected String[] profiles(String profile) {
        return profile.split(",");
    }

}
