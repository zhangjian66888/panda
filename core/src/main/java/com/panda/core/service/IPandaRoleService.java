package com.panda.core.service;

import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.PandaRolePermissionDto;
import com.panda.core.dto.search.PandaRoleSo;
import com.panda.core.entity.PandaRole;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
public interface IPandaRoleService extends IBaseService<PandaRole, PandaRoleDto, PandaRoleSo> {

    Set<PandaRolePermissionDto> permissionsByRoleId(Long roleId);

    Set<PandaRolePermissionDto> permissionsByRoleIds(Set<Long> roleIds);

    Set<Long> filterRoles(Set<Long> roleIds, List<Long> envCodes, Long appCode);

    Set<Long> permissionIdsByRoleIds(Set<Long> roleIds);

    Set<Long> permissionIdsByRoleIds(Set<Long> roleIds, List<Long> envCodes, Long appCode);

    int savePermission(PandaRolePermissionDto dto);

    int deletePermission(PandaRolePermissionDto dto);

    Set<Long> roleIds(List<Long> envCodes, Long appCode);
}
