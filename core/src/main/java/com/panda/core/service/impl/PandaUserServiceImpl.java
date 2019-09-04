package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.panda.common.enums.DelState;
import com.panda.common.exception.PandaException;
import com.panda.common.security.PasswordEncoder;
import com.panda.common.util.BeanUtil;
import com.panda.core.config.ConfigProperties;
import com.panda.core.dto.PandaUserDto;
import com.panda.core.dto.PandaUserRoleDto;
import com.panda.core.dto.search.PandaUserSo;
import com.panda.core.entity.PandaUser;
import com.panda.core.entity.PandaUserRole;
import com.panda.core.mapper.PandaUserMapper;
import com.panda.core.mapper.PandaUserRoleMapper;
import com.panda.core.security.SecurityUser;
import com.panda.core.security.SecurityUserContext;
import com.panda.core.service.IPandaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
public class PandaUserServiceImpl
        extends BaseServiceImpl<PandaUserMapper, PandaUser, PandaUserDto, PandaUserSo>
        implements IPandaUserService {

    @Autowired
    private PandaUserRoleMapper pandaUserRoleMapper;

    @Autowired
    private PandaUserMapper pandaUserMapper;

    @Autowired
    private ConfigProperties configProperties;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean save(PandaUser entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return super.save(entity);
    }

    @Override
    public List<PandaUserRoleDto> rolesByUserId(Long userId) {
        PandaUserRole query = new PandaUserRole();
        query.setUserId(userId);
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaUserRole> rpQueryWrapper = new QueryWrapper<>(query);
        rpQueryWrapper.select("id", "user_id", "role_id");
        rpQueryWrapper.nonEmptyOfEntity();
        List<PandaUserRole> list = pandaUserRoleMapper.selectList(rpQueryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList()).stream()
                .map(t -> PandaUserRoleDto.builder()
                        .userId(t.getUserId())
                        .roleId(t.getRoleId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> rolesIdsByUserId(Long userId) {
        PandaUserRole query = new PandaUserRole();
        query.setUserId(userId);
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaUserRole> rpQueryWrapper = new QueryWrapper<>(query);
        rpQueryWrapper.select("role_id");
        rpQueryWrapper.nonEmptyOfEntity();
        List<PandaUserRole> list = pandaUserRoleMapper.selectList(rpQueryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList()).stream()
                .map(t -> t.getRoleId())
                .collect(Collectors.toList());
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

    @Override
    public List<PandaUserDto> vagueFullQuery(String key) {
        return Optional.ofNullable(pandaUserMapper.vagueLike(key))
                .orElse(Lists.newArrayList()).stream().map(t -> BeanUtil.transBean(t, PandaUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public int resetPasswd(Long id) {
        LocalDateTime now = LocalDateTime.now();
        PandaUser entity = PandaUser.builder().password(passwordEncoder.encode(configProperties.getDefaultPasswd())).build();
        entity.setId(id);
        entity.setUpdateTime(now);
        return pandaUserMapper.updateById(entity);
    }

    @Override
    public int updatePasswd(String oldPasswd, String newPasswd) {
        SecurityUser user = SecurityUserContext.getContext();
        if (user == null) {
            throw new PandaException("请登录后重试");
        }
        PandaUser pandaUser = pandaUserMapper.selectById(user.getUserId());
        if (pandaUser == null) {
            throw new PandaException("帐号不存在");
        }
        if (!passwordEncoder.matches(oldPasswd, pandaUser.getPassword())) {
            throw new PandaException("旧密码输入错误");
        }
        LocalDateTime now = LocalDateTime.now();
        PandaUser entity = PandaUser.builder().password(passwordEncoder.encode(newPasswd)).build();
        entity.setId(pandaUser.getId());
        entity.setUpdateTime(now);
        return pandaUserMapper.updateById(entity);
    }
}
