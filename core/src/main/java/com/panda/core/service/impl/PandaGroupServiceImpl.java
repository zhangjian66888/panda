package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.panda.common.enums.DelState;
import com.panda.common.exception.PandaException;
import com.panda.core.dto.PandaGroupDto;
import com.panda.core.dto.PandaGroupRoleDto;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.search.PandaGroupSo;
import com.panda.core.entity.PandaGroup;
import com.panda.core.entity.PandaGroupRole;
import com.panda.core.mapper.PandaGroupMapper;
import com.panda.core.mapper.PandaGroupRoleMapper;
import com.panda.core.service.IPandaGroupService;
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
public class PandaGroupServiceImpl
        extends BaseServiceImpl<PandaGroupMapper, PandaGroup, PandaGroupDto, PandaGroupSo>
        implements IPandaGroupService {


    @Autowired
    private PandaGroupRoleMapper pandaGroupRoleMapper;

    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Override
    public List<PandaGroupRoleDto> selectedByGroupId(Long groupId) {
        PandaGroupRole query = new PandaGroupRole();
        query.setGroupId(groupId);
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaGroupRole> rpQueryWrapper = new QueryWrapper<>(query);
        rpQueryWrapper.select("id", "group_id", "role_id");
        rpQueryWrapper.nonEmptyOfEntity();
        List<PandaGroupRole> list = pandaGroupRoleMapper.selectList(rpQueryWrapper);
        if (Objects.isNull(list) || list.isEmpty()) {
            return Lists.newArrayList();
        }
        List<Long> roleIds = Lists.newArrayList();
        Map<Long, PandaGroupRoleDto> map = Maps.newHashMap();
        for (PandaGroupRole entity : list) {
            roleIds.add(entity.getRoleId());
            map.put(entity.getRoleId(), PandaGroupRoleDto.builder()
                    .groupId(entity.getGroupId())
                    .roleId(entity.getRoleId())
                    .build());
        }
        List<PandaRoleDto> dtos = iPandaRoleService.findListByIds(roleIds);
        for (PandaRoleDto dto : dtos) {
            Optional.ofNullable(map.get(dto.getId())).ifPresent(t -> {
                t.setRoleName(dto.getRoleName());
            });
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public int saveRole(PandaGroupRoleDto dto) {
        PandaGroupRole query = new PandaGroupRole();
        query.setRoleId(dto.getRoleId());
        query.setGroupId(dto.getGroupId());
        query.setDelState(DelState.NO.getId());
        PandaGroupRole entity = pandaGroupRoleMapper.selectOne(new QueryWrapper<>(query));
        if (Objects.nonNull(entity)) {
            throw new PandaException("请勿重复添加");
        }
        LocalDateTime now = LocalDateTime.now();
        entity = new PandaGroupRole();
        entity.setRoleId(dto.getRoleId());
        entity.setGroupId(dto.getGroupId());
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        return pandaGroupRoleMapper.insert(entity);
    }

    @Override
    public int deleteRole(PandaGroupRoleDto dto) {
        PandaGroupRole query = new PandaGroupRole();
        query.setRoleId(dto.getRoleId());
        query.setGroupId(dto.getGroupId());
        query.setDelState(DelState.NO.getId());
        PandaGroupRole entity = pandaGroupRoleMapper.selectOne(new QueryWrapper<>(query));
        if (Objects.isNull(entity)) {
            return 1;
        }
        PandaGroupRole delete = new PandaGroupRole();
        delete.setId(entity.getId());
        delete.setDelState(DelState.YES.getId());
        delete.setUpdateTime(LocalDateTime.now());
        return pandaGroupRoleMapper.updateById(delete);
    }
}
