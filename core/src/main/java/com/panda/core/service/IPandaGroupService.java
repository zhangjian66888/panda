package com.panda.core.service;

import com.panda.core.dto.PandaGroupDto;
import com.panda.core.dto.PandaGroupRoleDto;
import com.panda.core.dto.PandaGroupUserDto;
import com.panda.core.dto.search.PandaGroupSo;
import com.panda.core.entity.PandaGroup;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
public interface IPandaGroupService extends IBaseService<PandaGroup, PandaGroupDto, PandaGroupSo>, ISelectItem<PandaGroupSo> {


    List<PandaGroupRoleDto> rolesByGroupId(Long groupId);

    int saveRole(PandaGroupRoleDto dto);

    int deleteRole(PandaGroupRoleDto dto);

    List<PandaGroupUserDto> usersByGroupId(Long groupId);

    int saveUser(PandaGroupUserDto dto);

    int deleteUser(PandaGroupUserDto dto);

    List<PandaGroupUserDto> groupsByUserId(Long userId);

    List<Long> roleIdsByGroupIds(Collection<Long> ids);
}
