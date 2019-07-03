package com.panda.core.service;

import com.panda.core.dto.PandaGroupDto;
import com.panda.core.dto.PandaGroupRoleDto;
import com.panda.core.dto.search.PandaGroupSo;
import com.panda.core.entity.PandaGroup;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
public interface IPandaGroupService extends IBaseService<PandaGroup, PandaGroupDto, PandaGroupSo> {


    List<PandaGroupRoleDto> selectedByGroupId(Long groupId);

    int saveRole(PandaGroupRoleDto dto);

    int deleteRole(PandaGroupRoleDto dto);

}
