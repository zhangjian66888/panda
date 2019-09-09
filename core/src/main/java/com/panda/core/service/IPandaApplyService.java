package com.panda.core.service;

import com.panda.common.enums.ApplyState;
import com.panda.core.dto.PandaApplyDto;
import com.panda.core.dto.search.PandaApplySo;
import com.panda.core.entity.PandaApply;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-09-05
 */
public interface IPandaApplyService extends IBaseService<PandaApply, PandaApplyDto, PandaApplySo> {

    int cancelById(Long id);

    int approvalById(Long id, ApplyState applyState);
}
