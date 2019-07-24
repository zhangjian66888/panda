package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.panda.common.enums.DelState;
import com.panda.common.exception.PandaException;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.PandaRolePermissionDto;
import com.panda.core.dto.search.PandaRoleSo;
import com.panda.core.entity.PandaRole;
import com.panda.core.entity.PandaRolePermission;
import com.panda.core.mapper.PandaRoleMapper;
import com.panda.core.mapper.PandaRolePermissionMapper;
import com.panda.core.service.IPandaRoleService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private PandaRoleMapper pandaRoleMapper;

    @Override
    public Set<PandaRolePermissionDto> permissionsByRoleId(Long roleId) {
        return permissionsByRoleIds(Lists.newArrayList(roleId));
    }

    @Override
    public Set<PandaRolePermissionDto> permissionsByRoleIds(List<Long> roleIds) {
        PandaRolePermission query = new PandaRolePermission();
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaRolePermission> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.in("role_id", roleIds);
        queryWrapper.select("id", "role_id", "permission_id");
        queryWrapper.nonEmptyOfEntity();
        List<PandaRolePermission> list = pandaRolePermissionMapper.selectList(queryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList()).stream()
                .map(t -> PandaRolePermissionDto
                        .builder()
                        .permissionId(t.getPermissionId())
                        .roleId(t.getRoleId())
                        .build())
                .collect(Collectors.toSet());

    }

    @Override
    public Set<Long> filterRoles(Set<Long> roleIds, List<Long> envCodes, Long appCode) {
        if (Objects.isNull(roleIds) || roleIds.isEmpty()){
            return roleIds;
        }
        PandaRole query = new PandaRole();
        query.setDelState(DelState.NO.getId());
        query.setAppCode(appCode);
        QueryWrapper<PandaRole> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.in("env_code", envCodes);
        queryWrapper.in("id", roleIds);
        queryWrapper.select("id");
        queryWrapper.nonEmptyOfEntity();
        List<PandaRole> list = pandaRoleMapper.selectList(queryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList()).stream()
                .map(t -> t.getId()).collect(Collectors.toSet());
    }

    @Override
    public Set<Long> permissionIdsByRoleIds(Set<Long> roleIds) {
        PandaRolePermission query = new PandaRolePermission();
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaRolePermission> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.in("role_id", roleIds);
        queryWrapper.select("id", "role_id", "permission_id");
        queryWrapper.nonEmptyOfEntity();
        List<PandaRolePermission> list = pandaRolePermissionMapper.selectList(queryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList()).stream()
                .map(t -> t.getPermissionId())
                .distinct()
                .collect(Collectors.toSet());

    }

    @Override
    public Set<Long> permissionIdsByRoleIds(Set<Long> roleIds, List<Long> envCodes, Long appCode) {
        return Optional.ofNullable(filterRoles(roleIds, envCodes, appCode))
                .filter(t -> !t.isEmpty()).map(t -> permissionIdsByRoleIds(t)).orElse(Sets.newHashSet());
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
