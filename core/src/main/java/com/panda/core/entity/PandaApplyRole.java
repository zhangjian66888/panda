package com.panda.core.entity;

import com.panda.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PandaApplyRole extends BaseEntity {

    private Long applyId;

    private Long applicantId;

    private Long applyAppCode;

    private Integer applyState;

    private String applyContent;

    private Long approverId;

    private LocalDateTime approveTime;

    private String approveOpinion;
}
