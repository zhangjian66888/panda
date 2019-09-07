package com.panda.core.front.api;

import com.panda.common.dto.PageDto;
import com.panda.common.dto.ResultDto;
import com.panda.common.enums.ApplyState;
import com.panda.common.enums.ApplyType;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import com.panda.core.dto.PandaApplyDto;
import com.panda.core.dto.PandaApplyRoleDto;
import com.panda.core.dto.PandaRoleDto;
import com.panda.core.dto.search.PandaApplySo;
import com.panda.core.front.dto.search.FrontApplySo;
import com.panda.core.handler.ApplyHandler;
import com.panda.core.security.SecurityUser;
import com.panda.core.security.SecurityUserContext;
import com.panda.core.service.IPandaApplyRoleService;
import com.panda.core.service.IPandaApplyService;
import com.panda.core.service.IPandaRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * com.panda.core.front.api.FrontApplyController
 * <p>
 * DATE 2019/9/6
 *
 * @author zhanglijian.
 */

@RestController
@RequestMapping(CoreConst.FRONT_REQUEST_PREFIX + "apply")
public class FrontApplyController {

    @Autowired
    private IPandaApplyService iPandaApplyService;

    @Autowired
    private IPandaApplyRoleService iPandaApplyRoleService;

    @Autowired
    private IPandaRoleService iPandaRoleService;

    @Autowired
    private ApplyHandler applyHandler;

    @PostMapping("search")
    public Mono<ResultDto<PageDto<PandaApplyDto>>> search(@RequestBody FrontApplySo search) {
        return Mono.fromSupplier(() -> {
            SecurityUser uer = SecurityUserContext.getContext();
            PandaApplySo so = new PandaApplySo() {{
                setApplicantId(uer.getUserId());
                in("apply_type", search.getApplyTypes());
                in("apply_state", search.getApplyState());
            }};

            PageDto<PandaApplyDto> pageDto = iPandaApplyService.search(so);
            if (Objects.nonNull(pageDto)
                    && Objects.nonNull(pageDto.getRecords()) && !pageDto.getRecords().isEmpty()) {
                for (PandaApplyDto dto : pageDto.getRecords()) {
                    dto.setApplyTypeLabel(SelectItemUtil.getValueById(dto.getApplyType(), ApplyType.class));
                    dto.setApplyStateLabel(SelectItemUtil.getValueById(dto.getApplyState(), ApplyState.class));
                }
            }
            return ResultDto.SUCCESS().setData(pageDto);
        });
    }

    @GetMapping("detail")
    public Mono<ResultDto<List>> search(@RequestParam("id") Long id) {
        return Mono.fromSupplier(() -> {
            SecurityUser uer = SecurityUserContext.getContext();
            List<PandaApplyRoleDto> applyRoles = iPandaApplyRoleService
                    .find(PandaApplyRoleDto.builder().applyId(id).applicantId(uer.getUserId()).build());
            if (Objects.nonNull(applyRoles) && !applyRoles.isEmpty()) {
                Set<Long> roleIds = applyRoles.stream().flatMap(t -> t.roleIds().stream()).collect(Collectors.toSet());
                List<PandaRoleDto> roles = iPandaRoleService.fillRole(roleIds);
                Map<Long, PandaRoleDto> roleMap = roles.stream().collect(Collectors.toMap(t -> t.getId(), t -> t));
                for (PandaApplyRoleDto applyRole : applyRoles) {
                    applyRole.setApplyStateLabel(SelectItemUtil.getValueById(applyRole.getApplyState(), ApplyState.class));
                    for (Long roleId : applyRole.roleIds()) {
                        applyRole.addRole(roleMap.get(roleId));
                    }
                }
            }
            return ResultDto.SUCCESS().setData(applyRoles);
        });
    }

    @PostMapping("/delete")
    @ResponseBody
    public Mono<ResultDto> delete(@RequestParam(value = "id") Long id) {
        return Mono.fromSupplier(() -> {
            applyHandler.deleteApply(id);
            return ResultDto.SUCCESS();
        });
    }

    @PostMapping("/cancel")
    @ResponseBody
    public Mono<ResultDto> cancel(@RequestParam(value = "id") Long id) {
        return Mono.fromSupplier(() -> {
            applyHandler.cancelApply(id);
            return ResultDto.SUCCESS();
        });
    }
}
