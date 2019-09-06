package com.panda.core.handler;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.panda.common.enums.ApplyState;
import com.panda.common.enums.ApplyType;
import com.panda.common.exception.PandaException;
import com.panda.core.dto.PandaApplyDto;
import com.panda.core.dto.PandaApplyRoleDto;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.front.dto.FrontApplyRoleDto;
import com.panda.core.security.SecurityUser;
import com.panda.core.security.SecurityUserContext;
import com.panda.core.service.IPandaApplyRoleService;
import com.panda.core.service.IPandaApplyService;
import com.panda.core.service.IPandaRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * com.panda.core.handler.ResourceHandler
 * <p>
 * DATE 2019/9/6
 *
 * @author zhanglijian.
 */
@Component
public class ResourceHandler {

    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Autowired
    private IPandaApplyService iPandaApplyService;

    @Autowired
    private IPandaApplyRoleService iPandaApplyRoleService;

    public void apply(FrontApplyRoleDto applyRoleDto) {
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
                    .applyState(ApplyState.SUBMIT.getId())
                    .applyType(ApplyType.ROLE.getId())
                    .build();
            iPandaApplyService.insertOrUpdate(apply);
            List<PandaApplyRoleDto> applyRoles = map.entrySet().stream()
                    .map(t -> PandaApplyRoleDto.builder()
                            .applyId(apply.getId())
                            .applicantId(user.getUserId())
                            .applyAppCode(t.getKey())
                            .applyContent(JSON.toJSONString(t.getValue()))
                            .applyState(ApplyState.SUBMIT.getId())
                            .build()).collect(Collectors.toList());
            iPandaApplyRoleService.insertBatch(applyRoles);
        }
    }
}
