package com.panda.core.service.impl;

import com.panda.core.dto.PandaRolePermissionDto;
import com.panda.core.dto.search.PandaRolePermissionSo;
import com.panda.core.entity.PandaRolePermission;
import com.panda.core.mapper.PandaRolePermissionMapper;
import com.panda.core.service.IPandaRolePermissionService;
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
public class PandaRolePermissionServiceImpl
        extends BaseServiceImpl<PandaRolePermissionMapper, PandaRolePermission, PandaRolePermissionDto, PandaRolePermissionSo>
        implements IPandaRolePermissionService {

}
