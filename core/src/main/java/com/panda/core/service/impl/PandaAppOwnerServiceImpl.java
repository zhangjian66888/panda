package com.panda.core.service.impl;

import com.panda.core.dto.PandaAppOwnerDto;
import com.panda.core.dto.search.PandaAppOwnerSo;
import com.panda.core.entity.PandaAppOwner;
import com.panda.core.mapper.PandaAppOwnerMapper;
import com.panda.core.service.IPandaAppOwnerService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-07-29
 */
@Service
public class PandaAppOwnerServiceImpl extends BaseServiceImpl<PandaAppOwnerMapper, PandaAppOwner, PandaAppOwnerDto, PandaAppOwnerSo>
        implements IPandaAppOwnerService {
}
