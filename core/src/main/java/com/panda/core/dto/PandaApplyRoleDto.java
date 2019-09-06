package com.panda.core.dto;

import com.panda.common.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private Long applyAppCode;

    private Integer applyState;

    private String applyContent;

    private Long approverId;

    private LocalDateTime approveTime;

    private String approveOpinion;
}
