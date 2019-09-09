package com.panda.core.entity;

import com.panda.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class PandaApplyRole extends BaseEntity {

    private Long applyId;

    private Long applicantId;

    private String applicant;

    private Long appCode;

    private Integer applyState;

    private String applyContent;

    private Long approverId;

    private String approver;

    private LocalDateTime approveTime;

    private String approveOpinion;
}
