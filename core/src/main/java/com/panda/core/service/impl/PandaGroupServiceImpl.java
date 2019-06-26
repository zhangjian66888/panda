package com.panda.core.service.impl;

import com.panda.core.dto.PandaGroupDto;
import com.panda.core.dto.search.PandaGroupSo;
import com.panda.core.entity.PandaGroup;
import com.panda.core.mapper.PandaGroupMapper;
import com.panda.core.service.IPandaGroupService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
@Service
public class PandaGroupServiceImpl
        extends BaseServiceImpl<PandaGroupMapper, PandaGroup, PandaGroupDto, PandaGroupSo>
        implements IPandaGroupService {

}
