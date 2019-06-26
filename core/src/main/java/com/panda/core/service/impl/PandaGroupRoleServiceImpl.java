package com.panda.core.service.impl;

import com.panda.core.dto.PandaGroupRoleDto;
import com.panda.core.dto.search.PandaGroupRoleSo;
import com.panda.core.entity.PandaGroupRole;
import com.panda.core.mapper.PandaGroupRoleMapper;
import com.panda.core.service.IPandaGroupRoleService;
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
public class PandaGroupRoleServiceImpl
        extends BaseServiceImpl<PandaGroupRoleMapper, PandaGroupRole, PandaGroupRoleDto, PandaGroupRoleSo>
        implements IPandaGroupRoleService {

}
