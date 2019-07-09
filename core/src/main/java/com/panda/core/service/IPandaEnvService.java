package com.panda.core.service;

import com.panda.core.dto.PandaEnvDto;
import com.panda.core.dto.search.PandaEnvSo;
import com.panda.core.entity.PandaEnv;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-21
 */
public interface IPandaEnvService extends IBaseService<PandaEnv, PandaEnvDto, PandaEnvSo>,
        ISelectItem<PandaEnvSo> {

    List<Long> profileToCode(String ...profile);
}
