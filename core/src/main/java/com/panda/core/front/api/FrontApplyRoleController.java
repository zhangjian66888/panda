package com.panda.core.front.api;

/**
 * com.panda.core.front.api.FrontApplyRoleController
 * <p>
 * DATE 2019/9/9
 *
 * @author zhanglijian.
 */

import com.panda.common.dto.PageDto;
import com.panda.common.dto.ResultDto;
import com.panda.common.enums.AppOwnerType;
import com.panda.common.enums.ApplyState;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaApplyRoleDto;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.search.PandaApplyRoleSo;
import com.panda.core.front.dto.FrontApprovalDto;
import com.panda.core.front.dto.search.FrontApplyRoleSo;
import com.panda.core.handler.ApplyHandler;
import com.panda.core.security.SecurityUser;
import com.panda.core.security.SecurityUserContext;
import com.panda.core.service.IPandaAppService;
import com.panda.core.service.IPandaApplyRoleService;
import com.panda.core.service.IPandaRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(CoreConst.FRONT_REQUEST_PREFIX + "applyRole")
public class FrontApplyRoleController {

    @Autowired
    private IPandaApplyRoleService iPandaApplyRoleService;

    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Autowired
    private IPandaAppService iPandaAppService;

    @Autowired
    private ApplyHandler applyHandler;

    @PostMapping("search")
    public Mono<ResultDto<PageDto<PandaApplyRoleDto>>> search(@RequestBody FrontApplyRoleSo search) {

        return Mono.fromSupplier(() -> {
            SecurityUser uer = SecurityUserContext.getContext();
            List<Long> appCodes = iPandaAppService.findCodeByOwnerId(AppOwnerType.ADMIN, uer.getUserId());
            if (Objects.isNull(appCodes) || appCodes.isEmpty()) {
                return ResultDto.SUCCESS();
            }
            PandaApplyRoleSo so = new PandaApplyRoleSo() {{
                setApplicantId(uer.getUserId());
                setAppCode(search.getAppCode());
                in("apply_state", search.getApplyStates());
                in("app_code", appCodes);
            }};
            PageDto<PandaApplyRoleDto> pageDto = iPandaApplyRoleService.search(so);
            if (Objects.nonNull(pageDto)
                    && Objects.nonNull(pageDto.getRecords()) && !pageDto.getRecords().isEmpty()) {
                Set<Long> roleIds = pageDto.getRecords().stream().flatMap(t -> t.roleIds().stream()).collect(Collectors.toSet());
                List<PandaRoleDto> roles = iPandaRoleService.fillRole(roleIds);
                Map<Long, PandaRoleDto> roleMap = roles.stream().collect(Collectors.toMap(t -> t.getId(), t -> t));
                for (PandaApplyRoleDto applyRole : pageDto.getRecords()) {
                    applyRole.setApplyStateLabel(SelectItemUtil.getValueById(applyRole.getApplyState(), ApplyState.class));
                    for (Long roleId : applyRole.roleIds()) {
                        applyRole.addRole(roleMap.get(roleId));
                    }
                    if (Objects.nonNull(applyRole.getRoles()) && !applyRole.getRoles().isEmpty()) {
                        applyRole.setAppName(applyRole.getRoles().get(0).getAppName());
                    }
                    applyRole.setApplyStateLabel(SelectItemUtil.getValueById(applyRole.getApplyState(), ApplyState.class));
                }
            }
            return ResultDto.SUCCESS().setData(pageDto);
        });
    }


    @PostMapping("approval")
    public Mono<ResultDto> approval(@RequestBody FrontApprovalDto approvalDto) {
        return Mono.fromSupplier(() -> {
            applyHandler.approvalRole(approvalDto);
            return ResultDto.SUCCESS();
        });
    }
}
