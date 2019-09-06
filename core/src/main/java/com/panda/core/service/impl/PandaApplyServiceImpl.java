package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.panda.common.enums.ApplyState;
import com.panda.common.enums.DelState;
import com.panda.core.dto.PandaApplyDto;
import com.panda.core.dto.search.PandaApplySo;
import com.panda.core.entity.PandaApply;
import com.panda.core.mapper.PandaApplyMapper;
import com.panda.core.service.IPandaApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhanglijian
 * @since 2019-09-05
 */
@Service
public class PandaApplyServiceImpl
        extends BaseServiceImpl<PandaApplyMapper, PandaApply, PandaApplyDto, PandaApplySo>
        implements IPandaApplyService {

    @Autowired
    private PandaApplyMapper pandaApplyMapper;

    @Override
    public int cancelById(Long id) {
        return stateById(id, ApplyState.CANCEL, Lists.newArrayList(ApplyState.SUBMIT, ApplyState.DOING));
    }

    private int stateById(Long id, ApplyState applyState, List<ApplyState> applyStates) {
        LocalDateTime now = LocalDateTime.now();
        PandaApply update = new PandaApply() {{
            setApplyState(applyState.getId());
            setUpdateTime(now);
        }};
        PandaApply where = new PandaApply() {{
            setId(id);
            setDelState(DelState.NO.getId());
        }};
        UpdateWrapper<PandaApply> updateWrapper = new UpdateWrapper<>(where);
        if (Objects.nonNull(applyStates) && !applyStates.isEmpty()) {
            updateWrapper.in("apply_state", ApplyState.SUBMIT.getId(), ApplyState.DOING.getId());
        }
        return pandaApplyMapper.update(update, updateWrapper);
    }
}
