package com.panda.core.service;

import com.panda.core.dto.PandaTokenDto;
import com.panda.core.dto.search.PandaTokenSo;
import com.panda.core.entity.PandaToken;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-07-04
 */
public interface IPandaTokenService extends IBaseService<PandaToken, PandaTokenDto, PandaTokenSo> {

    PandaTokenDto validToken(Long userId);

}
