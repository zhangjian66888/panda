package com.panda.core.service.impl;

import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.search.PandaRoleSo;
import com.panda.core.entity.PandaRole;
import com.panda.core.mapper.PandaRoleMapper;
import com.panda.core.service.IPandaRoleService;
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
public class PandaRoleServiceImpl
        extends BaseServiceImpl<PandaRoleMapper, PandaRole, PandaRoleDto, PandaRoleSo>
        implements IPandaRoleService {

}
