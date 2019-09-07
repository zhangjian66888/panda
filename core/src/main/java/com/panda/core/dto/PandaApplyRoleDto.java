package com.panda.core.dto;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.panda.common.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *
 * </p>
 *
 * @author zhanglijian
 * @since 2019-09-05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PandaApplyRoleDto extends BaseDto {

    private Long applyId;

    private Long applicantId;

    private String applicant;

    private Long applyAppCode;

    private Integer applyState;

    private String applyContent;

    private List<PandaRoleDto> roles;

    private Long approverId;

    private String approver;

    private LocalDateTime approveTime;

    private String approveOpinion;

    public PandaApplyRoleDto addRole(PandaRoleDto role) {
        this.roles = Optional.ofNullable(roles).orElse(Lists.newArrayList());
        Optional.ofNullable(role).ifPresent(t->this.roles.add(t));
        return this;
    }

    public List<Long> roleIds() {
        return Optional.ofNullable(applyContent).map(t -> JSON.parseArray(t, Long.class)).orElse(Lists.newArrayList());
    }


}
