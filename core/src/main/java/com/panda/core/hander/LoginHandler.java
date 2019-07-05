package com.panda.core.hander;

import com.panda.common.dto.StatusDto;
import com.panda.common.exception.PandaException;
import com.panda.common.util.MD5Util;
import com.panda.common.util.TokenUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.LoginDto;
import com.panda.core.dto.LoginSuccessDto;
import com.panda.core.dto.PandaTokenDto;
import com.panda.core.dto.PandaUserDto;
import com.panda.core.service.IPandaTokenService;
import com.panda.core.service.IPandaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * com.panda.core.hander.LoginHandler
 * <p>
 * DATE 2019/7/4
 *
 * @author zhanglijian.
 */

@Component
public class LoginHandler {

    @Autowired
    private IPandaUserService iPandaUserService;

    @Autowired
    private IPandaTokenService iPandaTokenService;

    public LoginSuccessDto login(LoginDto dto) {
        String errorMsg = "账号或密码错误";
        PandaUserDto pandaUserDto = iPandaUserService.findOne(PandaUserDto.builder().username(dto.getUsername()).build());
        if (Objects.isNull(pandaUserDto)) {
            throw new PandaException(errorMsg);
        }
        if (!MD5Util.saltverifyMd5AndSha(dto.getPassword(), pandaUserDto.getPassword())) {
            throw new PandaException(errorMsg);
        }
        PandaTokenDto old = iPandaTokenService.validToken(pandaUserDto.getId());
        PandaTokenDto update = new PandaTokenDto();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expireTime = now.plusDays(CoreConst.TOKEN_VALID_DAY);
        LoginSuccessDto successDto = new LoginSuccessDto();
        if (Objects.nonNull(old)) {
            update.setId(old.getId());
            update.setExpireTime(expireTime);
            update.setUpdateTime(now);
            successDto.setAccessToken(old.getAccessToken());
        } else {
            update.setAccessToken(TokenUtil.token());
            update.setUserId(pandaUserDto.getId());
            update.setExpireTime(expireTime);
            update.setUpdateTime(now);
            update.setCreateTime(now);
            successDto.setAccessToken(update.getAccessToken());
        }
        successDto.setExpireTime(expireTime);
        successDto.setZhName(pandaUserDto.getZhName());
        iPandaTokenService.insertOrUpdate(update);
        return successDto;
    }
}
