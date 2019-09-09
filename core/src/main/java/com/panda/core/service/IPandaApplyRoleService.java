package com.panda.core.service;

import com.panda.common.enums.ApplyState;
import com.panda.core.dto.PandaApplyRoleDto;
import com.panda.core.dto.search.PandaApplyRoleSo;
import com.panda.core.entity.PandaApplyRole;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-09-05
 */
public interface IPandaApplyRoleService extends IBaseService<PandaApplyRole, PandaApplyRoleDto, PandaApplyRoleSo> {

    int deleteByApplyId(Long applyId);

    int cancelByApplyId(Long applyId);

   int approvalById(Long id, ApplyState applyState);
}
