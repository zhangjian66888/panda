package com.panda.core.service.impl;

import com.panda.core.dto.PandaAppSecretDto;
import com.panda.core.dto.search.PandaAppSecretSo;
import com.panda.core.entity.PandaAppSecret;
import com.panda.core.mapper.PandaAppSecretMapper;
import com.panda.core.service.IPandaAppSecretService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-21
 */
@Service
public class PandaAppSecretServiceImpl
        extends BaseServiceImpl<PandaAppSecretMapper, PandaAppSecret, PandaAppSecretDto, PandaAppSecretSo>
        implements IPandaAppSecretService {

}
