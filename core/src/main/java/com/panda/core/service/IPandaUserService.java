package com.panda.core.service;

import com.panda.core.dto.PandaUserDto;
import com.panda.core.dto.PandaUserRoleDto;
import com.panda.core.dto.search.PandaUserSo;
import com.panda.core.entity.PandaUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-26
 */
public interface IPandaUserService extends IBaseService<PandaUser, PandaUserDto, PandaUserSo> {


    List<PandaUserRoleDto> rolesByUserId(Long userId);

    int saveRole(PandaUserRoleDto dto);

    int deleteRole(PandaUserRoleDto dto);

    List<PandaUserDto> vagueFullQuery(String key);
}
