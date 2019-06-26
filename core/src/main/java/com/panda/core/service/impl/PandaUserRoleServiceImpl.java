package com.panda.core.service.impl;

import com.panda.core.dto.PandaUserRoleDto;
import com.panda.core.dto.search.PandaUserRoleSo;
import com.panda.core.entity.PandaUserRole;
import com.panda.core.mapper.PandaUserRoleMapper;
import com.panda.core.service.IPandaUserRoleService;
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
public class PandaUserRoleServiceImpl
        extends BaseServiceImpl<PandaUserRoleMapper, PandaUserRole, PandaUserRoleDto, PandaUserRoleSo>
        implements IPandaUserRoleService {

}
