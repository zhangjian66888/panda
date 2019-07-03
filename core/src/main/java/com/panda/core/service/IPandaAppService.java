package com.panda.core.service;

import com.panda.core.dto.PandaAppDto;
import com.panda.core.dto.PandaAppSecretDto;
import com.panda.core.dto.search.PandaAppSo;
import com.panda.core.entity.PandaApp;

import java.util.List;

/**
 * <p>
 * 应用 服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-03
 */
public interface IPandaAppService extends IBaseService<PandaApp, PandaAppDto, PandaAppSo>,
        ISelectItem<PandaAppSo> {

    List<PandaAppSecretDto> secretByAppCode(Long code);

    String flushSecret(PandaAppSecretDto dto);

}
