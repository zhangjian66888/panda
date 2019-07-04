package com.panda.core.service;

import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.PandaRolePermissionDto;
import com.panda.core.dto.search.PandaRoleSo;
import com.panda.core.entity.PandaRole;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
public interface IPandaRoleService extends IBaseService<PandaRole, PandaRoleDto, PandaRoleSo> {

    List<PandaRolePermissionDto> permissionsByRoleId(Long roleId);

    int savePermission(PandaRolePermissionDto dto);

    int deletePermission(PandaRolePermissionDto dto);

}
