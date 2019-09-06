package com.panda.core.service.impl;

import com.panda.core.dto.PandaApplyRoleDto;
import com.panda.core.dto.search.PandaApplyRoleSo;
import com.panda.core.entity.PandaApplyRole;
import com.panda.core.mapper.PandaApplyRoleMapper;
import com.panda.core.service.IPandaApplyRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-09-05
 */
@Service
public class PandaApplyRoleServiceImpl
        extends BaseServiceImpl<PandaApplyRoleMapper, PandaApplyRole, PandaApplyRoleDto, PandaApplyRoleSo>
        implements IPandaApplyRoleService {

}
