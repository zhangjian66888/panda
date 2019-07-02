package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.panda.common.enums.DelState;
import com.panda.common.exception.PandaException;
import com.panda.core.dto.PandaPermissionDto;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.PandaRolePermissionDto;
import com.panda.core.dto.search.PandaRoleSo;
import com.panda.core.entity.PandaRole;
import com.panda.core.entity.PandaRolePermission;
import com.panda.core.mapper.PandaRoleMapper;
import com.panda.core.mapper.PandaRolePermissionMapper;
import com.panda.core.service.IPandaPermissionService;
import com.panda.core.service.IPandaRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private PandaRolePermissionMapper pandaRolePermissionMapper;

    @Autowired
    private IPandaPermissionService iPandaPermissionService;

    @Override
    public List<PandaRolePermissionDto> selectedByRoleId(Long roleId) {
        PandaRolePermission rpQuery = new PandaRolePermission();
        rpQuery.setRoleId(roleId);
        rpQuery.setDelState(DelState.NO.getId());
        QueryWrapper<PandaRolePermission> rpQueryWrapper = new QueryWrapper<>(rpQuery);
        rpQueryWrapper.select("id", "role_id", "permission_id");
        rpQueryWrapper.nonEmptyOfEntity();
        List<PandaRolePermission> rpList = pandaRolePermissionMapper.selectList(rpQueryWrapper);
        if (Objects.isNull(rpList) || rpList.isEmpty()) {
            return Lists.newArrayList();
        }
        List<Long> permissionIds = Lists.newArrayList();
        Map<Long, PandaRolePermissionDto> rpMap = Maps.newHashMap();
        for (PandaRolePermission rp : rpList) {
            permissionIds.add(rp.getPermissionId());
            rpMap.put(rp.getPermissionId(), PandaRolePermissionDto.builder()
                    .permissionId(rp.getPermissionId())
                    .roleId(rp.getRoleId())
                    .build());
        }
        List<PandaPermissionDto> permissions = iPandaPermissionService.findListByIds(permissionIds);
        for (PandaPermissionDto permissionDto : permissions) {
            Optional.ofNullable(rpMap.get(permissionDto.getId())).ifPresent(t -> {
                t.setPermissionName(permissionDto.getName());
                t.setPermissionShowName(permissionDto.getShowName());

            });
        }
        return new ArrayList<>(rpMap.values());
    }

    @Override
    public int savePermission(PandaRolePermissionDto dto) {
        PandaRolePermission rpQuery = new PandaRolePermission();
        rpQuery.setRoleId(dto.getRoleId());
        rpQuery.setPermissionId(dto.getPermissionId());
        rpQuery.setDelState(DelState.NO.getId());
        PandaRolePermission rolePermission = pandaRolePermissionMapper.selectOne(new QueryWrapper<>(rpQuery));
        if (Objects.nonNull(rolePermission)) {
            throw new PandaException("请勿重复添加");
        }
        LocalDateTime now = LocalDateTime.now();
        rolePermission = new PandaRolePermission();
        rolePermission.setRoleId(dto.getRoleId());
        rolePermission.setPermissionId(dto.getPermissionId());
        rolePermission.setCreateTime(now);
        rolePermission.setUpdateTime(now);
        return pandaRolePermissionMapper.insert(rolePermission);
    }

    @Override
    public int deletePermission(PandaRolePermissionDto dto) {
        PandaRolePermission rpQuery = new PandaRolePermission();
        rpQuery.setRoleId(dto.getRoleId());
        rpQuery.setPermissionId(dto.getPermissionId());
        rpQuery.setDelState(DelState.NO.getId());
        PandaRolePermission rolePermission = pandaRolePermissionMapper.selectOne(new QueryWrapper<>(rpQuery));
        if (Objects.isNull(rolePermission)) {
            return 1;
        }
        PandaRolePermission delete = new PandaRolePermission();
        delete.setId(rolePermission.getId());
        delete.setDelState(DelState.YES.getId());
        delete.setUpdateTime(LocalDateTime.now());
        return pandaRolePermissionMapper.updateById(delete);
    }
}
