package com.panda.core.hander;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.panda.common.enums.DelState;
import com.panda.core.dto.PandaGroupRoleDto;
import com.panda.core.dto.PandaGroupUserDto;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.PandaUserDto;
import com.panda.core.entity.PandaGroupRole;
import com.panda.core.entity.PandaGroupUser;
import com.panda.core.service.IPandaGroupUserService;
import com.panda.core.service.IPandaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * com.panda.core.hander.PandaGroupUserHandler
 * <p>
 * DATE 2019/7/3
 *
 * @author zhanglijian.
 */
@Component
public class PandaGroupUserHandler {

    @Autowired
    private IPandaGroupUserService iPandaGroupUserService;

    @Autowired
    private IPandaUserService iPandaUserService;

    public List<PandaGroupUserDto> usersByGroupId(Long groupId) {
        PandaGroupUser query = new PandaGroupUser();
        query.setGroupId(groupId);
        query.setDelState(DelState.NO.getId());
        QueryWrapper<PandaGroupUser> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.select("id", "group_id", "user_id");
        queryWrapper.nonEmptyOfEntity();
        List<PandaGroupUser> list = iPandaGroupUserService.list(queryWrapper);
        if (Objects.isNull(list) || list.isEmpty()) {
            return Lists.newArrayList();
        }
        List<Long> userIds = Lists.newArrayList();
        Map<Long, PandaGroupUserDto> map = Maps.newHashMap();
        for (PandaGroupUser entity : list) {
            userIds.add(entity.getUserId());
            map.put(entity.getUserId(), PandaGroupUserDto.builder()
                    .groupId(entity.getGroupId())
                    .userId(entity.getUserId())
                    .build());
        }
        List<PandaUserDto> dtos = iPandaUserService.findListByIds(userIds);
        for (PandaUserDto dto : dtos) {
            Optional.ofNullable(map.get(dto.getId())).ifPresent(t -> {
                t.setZhName(dto.getZhName());
                t.setEmail(dto.getEmail());
                t.setMobile(dto.getMobile());
            });
        }
        return new ArrayList<>(map.values());
    }

}
