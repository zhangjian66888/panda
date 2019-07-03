package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.panda.common.enums.DelState;
import com.panda.common.exception.PandaException;
import com.panda.common.util.MD5Utils;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.PandaUserDto;
import com.panda.core.dto.PandaUserRoleDto;
import com.panda.core.dto.search.PandaUserSo;
import com.panda.core.entity.PandaUser;
import com.panda.core.entity.PandaUserRole;
import com.panda.core.mapper.PandaUserMapper;
import com.panda.core.mapper.PandaUserRoleMapper;
import com.panda.core.service.IPandaRoleService;
import com.panda.core.service.IPandaUserService;
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
public class PandaUserServiceImpl
        extends BaseServiceImpl<PandaUserMapper, PandaUser, PandaUserDto, PandaUserSo>
        implements IPandaUserService {

    @Autowired
    private PandaUserRoleMapper pandaUserRoleMapper;

    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Override
    public boolean save(PandaUser entity) {
        entity.setPassword(MD5Utils.getSaltMd5AndSha(entity.getPassword()));
        return super.save(entity);
    }

    @Override
    public List<PandaUserRoleDto> selectedByUserId(Long userId) {
        PandaUserRole query = new PandaUserRole();
        query.setUserId(userId);
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaUserRole> rpQueryWrapper = new QueryWrapper<>(query);
        rpQueryWrapper.select("id", "user_id", "role_id");
        rpQueryWrapper.nonEmptyOfEntity();
        List<PandaUserRole> list = pandaUserRoleMapper.selectList(rpQueryWrapper);
        if (Objects.isNull(list) || list.isEmpty()) {
            return Lists.newArrayList();
        }
        List<Long> roleIds = Lists.newArrayList();
        Map<Long, PandaUserRoleDto> map = Maps.newHashMap();
        for (PandaUserRole entity : list) {
            roleIds.add(entity.getRoleId());
            map.put(entity.getRoleId(), PandaUserRoleDto.builder()
                    .userId(entity.getUserId())
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
    public int saveRole(PandaUserRoleDto dto) {
        PandaUserRole query = new PandaUserRole();
        query.setRoleId(dto.getRoleId());
        query.setUserId(dto.getUserId());
        query.setDelState(DelState.NO.getId());
        PandaUserRole entity = pandaUserRoleMapper.selectOne(new QueryWrapper<>(query));
        if (Objects.nonNull(entity)) {
            throw new PandaException("请勿重复添加");
        }
        LocalDateTime now = LocalDateTime.now();
        entity = new PandaUserRole();
        entity.setRoleId(dto.getRoleId());
        entity.setUserId(dto.getUserId());
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        return pandaUserRoleMapper.insert(entity);
    }

    @Override
    public int deleteRole(PandaUserRoleDto dto) {
        PandaUserRole query = new PandaUserRole();
        query.setRoleId(dto.getRoleId());
        query.setUserId(dto.getUserId());
        query.setDelState(DelState.NO.getId());
        PandaUserRole entity = pandaUserRoleMapper.selectOne(new QueryWrapper<>(query));
        if (Objects.isNull(entity)) {
            return 1;
        }
        PandaUserRole delete = new PandaUserRole();
        delete.setId(entity.getId());
        delete.setDelState(DelState.YES.getId());
        delete.setUpdateTime(LocalDateTime.now());
        return pandaUserRoleMapper.updateById(delete);
    }
}
