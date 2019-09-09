package com.panda.core.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.panda.common.enums.AppOwnerType;
import com.panda.common.enums.ApplyState;
import com.panda.common.enums.ApplyType;
import com.panda.common.exception.PandaException;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.dto.PandaAppOwnerDto;
import com.panda.core.dto.PandaApplyDto;
import com.panda.core.dto.PandaApplyRoleDto;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.front.dto.FrontApplyRoleDto;
import com.panda.core.front.dto.FrontApprovalDto;
import com.panda.core.security.SecurityUser;
import com.panda.core.security.SecurityUserContext;
import com.panda.core.service.IPandaAppService;
import com.panda.core.service.IPandaApplyRoleService;
import com.panda.core.service.IPandaApplyService;
import com.panda.core.service.IPandaRoleService;
import com.panda.core.service.IPandaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * com.panda.core.handler.ApplyHandler
 * <p>
 * DATE 2019/9/6
 *
 * @author zhanglijian.
 */
@Component
public class ApplyHandler {

    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Autowired
    private IPandaUserService iPandaUserService;

    @Autowired
    private IPandaAppService iPandaAppService;

    @Autowired
    private IPandaApplyService iPandaApplyService;

    @Autowired
    private IPandaApplyRoleService iPandaApplyRoleService;

    public void applyRole(FrontApplyRoleDto applyRoleDto) {
        SecurityUser user = SecurityUserContext.getContext();
        List<Long> roleIds = applyRoleDto.getRoleIds();
        PandaApplyDto check = iPandaApplyService.findOne(new PandaApplyDto() {{
            setApplicantId(user.getUserId());
            setApplyType(ApplyType.ROLE.getId());
            in("apply_state", Lists.newArrayList(ApplyState.SUBMIT.getId(), ApplyState.DOING.getId()));
        }});
        if (Objects.nonNull(check)) {
            throw new PandaException("已经有进行中申请");
        }
        List<PandaRoleDto> roles = iPandaRoleService.findListByIds(roleIds);
        if (Objects.nonNull(roleIds) && !roleIds.isEmpty()) {
            Map<Long, List<Long>> map = roles.stream()
                    .collect(Collectors.groupingBy(t -> t.getAppCode(), Collectors.mapping(t -> t.getId(), Collectors.toList())));
            PandaApplyDto apply = PandaApplyDto.builder()
                    .applicantId(user.getUserId())
                    .applicant(user.getZhName())
                    .applyState(ApplyState.SUBMIT.getId())
                    .applyType(ApplyType.ROLE.getId())
                    .build();
            iPandaApplyService.insertOrUpdate(apply);
            List<PandaApplyRoleDto> applyRoles = map.entrySet().stream()
                    .map(t -> PandaApplyRoleDto.builder()
                            .applyId(apply.getId())
                            .applicantId(user.getUserId())
                            .applicant(user.getZhName())
                            .appCode(t.getKey())
                            .applyContent(JSON.toJSONString(t.getValue()))
                            .applyState(ApplyState.SUBMIT.getId())
                            .build()).collect(Collectors.toList());
            iPandaApplyRoleService.insertBatch(applyRoles);
        }
    }

    public void approvalRole(FrontApprovalDto approvalDto) {
        PandaApplyRoleDto applyRole = iPandaApplyRoleService.findById(approvalDto.getApprovalId());
        if (Objects.isNull(applyRole)) {
            throw new PandaException("审批记录不存在或已删除");
        }
        if (ApplyState.SUBMIT.getId() != applyRole.getApplyState()) {
            throw new PandaException("状态已发生变化，无法操作");
        }
        SecurityUser user = SecurityUserContext.getContext();
        PandaAppOwnerDto pandaAppOwnerDto = iPandaAppService
                .findByAppCodeOwnerId(applyRole.getAppCode(), user.getUserId(), AppOwnerType.ADMIN);
        if (Objects.isNull(pandaAppOwnerDto)) {
            throw new PandaException("你无权操作，请联系应用管理员");
        }
        List<PandaApplyRoleDto> list = iPandaApplyRoleService.find(PandaApplyRoleDto.builder()
                .applyId(applyRole.getApplyId()).build());

        Map<Integer, Set<Long>> map = list.stream().filter(t -> !t.getId().equals(applyRole.getId()))
                .collect(Collectors.groupingBy(t -> t.getApplyState(), Collectors.mapping(t -> t.getId(), Collectors.toSet())));

        ApplyState applyState = SelectItemUtil.getById(approvalDto.getApplyState(), ApplyState.class);

        iPandaUserService.batchSaveRole(user.getUserId(), applyRole.roleIds());
        iPandaApplyRoleService.approvalById(approvalDto.getApprovalId(), applyState);
        Set<Long> tmp = map.get(applyState.getId());
        if (Objects.isNull(tmp)) {
            tmp = Sets.newHashSet();
            map.put(applyState.getId(), tmp);
        }
        tmp.add(applyRole.getId());
        ApplyState state = ApplyState.PASS;
        if (Objects.nonNull(map.get(ApplyState.SUBMIT.getId()))) {
            state = ApplyState.DOING;
        } else if (Objects.nonNull(map.get(ApplyState.PASS)) && Objects.nonNull(map.get(ApplyState.BACK))) {
            state = ApplyState.PB;
        } else if (Objects.isNull(map.get(ApplyState.PASS)) && Objects.nonNull(map.get(ApplyState.BACK))) {
            state = ApplyState.BACK;
        } else {
            state = ApplyState.PASS;
        }
        iPandaApplyService.approvalById(applyRole.getApplyId(), state);
    }


    public void deleteApply(Long id) {
        iPandaApplyService.deleteById(id);
        iPandaApplyRoleService.deleteByApplyId(id);
    }

    public void cancelApply(Long id) {
        iPandaApplyService.cancelById(id);
        iPandaApplyRoleService.cancelByApplyId(id);
    }

}
