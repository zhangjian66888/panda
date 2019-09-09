package com.panda.core.dto.search;

import com.panda.common.dto.BaseSo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class PandaApplyRoleSo extends BaseSo {

    private Long applyId;

    private Long applicantId;

    private Long appCode;

    private Integer applyState;

    private Long approverId;
}
