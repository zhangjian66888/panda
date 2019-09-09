package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Lists;
import com.panda.common.enums.ApplyState;
import com.panda.common.enums.DelState;
import com.panda.core.dto.PandaApplyRoleDto;
import com.panda.core.dto.search.PandaApplyRoleSo;
import com.panda.core.entity.PandaApplyRole;
import com.panda.core.mapper.PandaApplyRoleMapper;
import com.panda.core.service.IPandaApplyRoleService;
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
public class PandaApplyRoleServiceImpl
        extends BaseServiceImpl<PandaApplyRoleMapper, PandaApplyRole, PandaApplyRoleDto, PandaApplyRoleSo>
        implements IPandaApplyRoleService {

    @Autowired
    private PandaApplyRoleMapper pandaApplyRoleMapper;

    @Override
    public int deleteByApplyId(Long applyId) {
        LocalDateTime now = LocalDateTime.now();
        PandaApplyRole update = new PandaApplyRole() {{
            setDelState(DelState.YES.getId());
            setUpdateTime(now);
        }};
        PandaApplyRole where = PandaApplyRole.builder().applyId(applyId).build();
        where.setDelState(DelState.NO.getId());
        UpdateWrapper<PandaApplyRole> updateWrapper = new UpdateWrapper<>(where);
        updateWrapper.in("apply_state", ApplyState.SUBMIT.getId(), ApplyState.DOING.getId());
        return pandaApplyRoleMapper.update(update, updateWrapper);
    }

    @Override
    public int cancelByApplyId(Long applyId) {
        return stateByApplyId(applyId, ApplyState.CANCEL, Lists.newArrayList(ApplyState.SUBMIT, ApplyState.DOING));
    }

    @Override
    public int approvalById(Long id, ApplyState applyState) {
        LocalDateTime now = LocalDateTime.now();
        PandaApplyRole update = new PandaApplyRole();
        update.setId(id);
        update.setApplyState(applyState.getId());
        update.setUpdateTime(now);
        return pandaApplyRoleMapper.updateById(update);
    }

    private int stateByApplyId(Long applyId, ApplyState applyState, List<ApplyState> applyStates) {
        LocalDateTime now = LocalDateTime.now();
        PandaApplyRole update = new PandaApplyRole() {{
            setApplyState(applyState.getId());
            setUpdateTime(now);
        }};
        PandaApplyRole where = PandaApplyRole.builder().applyId(applyId).build();
        where.setDelState(DelState.NO.getId());
        UpdateWrapper<PandaApplyRole> updateWrapper = new UpdateWrapper<>(where);
        if (Objects.nonNull(applyStates) && !applyStates.isEmpty()) {
            updateWrapper.in("apply_state", ApplyState.SUBMIT.getId(), ApplyState.DOING.getId());
        }
        return pandaApplyRoleMapper.update(update, updateWrapper);
    }
}
