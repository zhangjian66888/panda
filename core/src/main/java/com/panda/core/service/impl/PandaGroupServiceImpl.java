package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.panda.common.enums.DelState;
import com.panda.common.exception.PandaException;
import com.panda.core.dto.PandaGroupDto;
import com.panda.core.dto.PandaGroupRoleDto;
import com.panda.core.dto.PandaGroupUserDto;
import com.panda.core.dto.search.PandaGroupSo;
import com.panda.core.entity.PandaGroup;
import com.panda.core.entity.PandaGroupRole;
import com.panda.core.entity.PandaGroupUser;
import com.panda.core.mapper.PandaGroupMapper;
import com.panda.core.mapper.PandaGroupRoleMapper;
import com.panda.core.mapper.PandaGroupUserMapper;
import com.panda.core.service.IPandaGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
public class PandaGroupServiceImpl
        extends BaseServiceImpl<PandaGroupMapper, PandaGroup, PandaGroupDto, PandaGroupSo>
        implements IPandaGroupService {


    @Autowired
    private PandaGroupRoleMapper pandaGroupRoleMapper;

    @Autowired
    private PandaGroupUserMapper pandaGroupUserMapper;

    @Override
    public String[] selectItemField() {
        return new String[]{"id", "groupName"};
    }

    @Override
    public List<PandaGroupRoleDto> rolesByGroupId(Long groupId) {
        PandaGroupRole query = new PandaGroupRole();
        query.setGroupId(groupId);
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaGroupRole> rpQueryWrapper = new QueryWrapper<>(query);
        rpQueryWrapper.select("id", "group_id", "role_id");
        rpQueryWrapper.nonEmptyOfEntity();
        List<PandaGroupRole> list = pandaGroupRoleMapper.selectList(rpQueryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList()).stream()
                .map(t -> PandaGroupRoleDto.builder()
                        .groupId(t.getGroupId())
                        .roleId(t.getRoleId())
                        .build())
                .collect(Collectors.toList());
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

    @Override
    public List<PandaGroupUserDto> usersByGroupId(Long groupId) {
        PandaGroupUser query = new PandaGroupUser();
        query.setGroupId(groupId);
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaGroupUser> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.select("id", "group_id", "user_id");
        queryWrapper.nonEmptyOfEntity();
        List<PandaGroupUser> list = pandaGroupUserMapper.selectList(queryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList()).stream()
                .map(t -> PandaGroupUserDto.builder()
                        .groupId(t.getGroupId())
                        .userId(t.getUserId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public int saveUser(PandaGroupUserDto dto) {
        PandaGroupUser query = new PandaGroupUser();
        query.setUserId(dto.getUserId());
        query.setGroupId(dto.getGroupId());
        query.setDelState(DelState.NO.getId());
        PandaGroupUser entity = pandaGroupUserMapper.selectOne(new QueryWrapper<>(query));
        if (Objects.nonNull(entity)) {
            throw new PandaException("请勿重复添加");
        }
        LocalDateTime now = LocalDateTime.now();
        entity = new PandaGroupUser();
        entity.setUserId(dto.getUserId());
        entity.setGroupId(dto.getGroupId());
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        return pandaGroupUserMapper.insert(entity);
    }

    @Override
    public int deleteUser(PandaGroupUserDto dto) {
        PandaGroupUser query = new PandaGroupUser();
        query.setUserId(dto.getUserId());
        query.setGroupId(dto.getGroupId());
        query.setDelState(DelState.NO.getId());
        PandaGroupUser entity = pandaGroupUserMapper.selectOne(new QueryWrapper<>(query));
        if (Objects.isNull(entity)) {
            return 1;
        }
        PandaGroupUser delete = new PandaGroupUser();
        delete.setId(entity.getId());
        delete.setDelState(DelState.YES.getId());
        delete.setUpdateTime(LocalDateTime.now());
        return pandaGroupUserMapper.updateById(delete);
    }

    @Override
    public List<PandaGroupUserDto> groupsByUserId(Long userId) {
        PandaGroupUser query = new PandaGroupUser();
        query.setUserId(userId);
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaGroupUser> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.select("id", "group_id", "user_id");
        queryWrapper.nonEmptyOfEntity();
        List<PandaGroupUser> list = pandaGroupUserMapper.selectList(queryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList()).stream()
                .map(t -> PandaGroupUserDto.builder()
                        .groupId(t.getGroupId())
                        .userId(t.getUserId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> roleIdsByGroupIds(Collection<Long> groupIds) {
        PandaGroupRole query = new PandaGroupRole();
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaGroupRole> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.in("group_id", groupIds);
        queryWrapper.select("role_id");
        queryWrapper.nonEmptyOfEntity();
        List<PandaGroupRole> list = pandaGroupRoleMapper.selectList(queryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList()).stream()
                .map(t -> t.getRoleId())
                .collect(Collectors.toList());
    }
}
