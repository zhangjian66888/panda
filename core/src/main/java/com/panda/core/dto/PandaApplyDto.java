package com.panda.core.dto;

import com.panda.common.dto.BaseDto;
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
public class PandaApplyDto extends BaseDto {

    private Long applicantId;

    private String applicant;

    private Integer applyType;
    private String applyTypeLabel;

    private Integer applyState;
    private String applyStateLabel;


}
