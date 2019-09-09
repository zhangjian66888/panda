package com.panda.core.front.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.panda.core.front.dto.FrontApprovalDto
 * <p>
 * DATE 2019/9/9
 *
 * @author zhanglijian.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontApprovalDto {

    private Long approvalId;

    private Integer applyState;

    private String approveOpinion;

}
