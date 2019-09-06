package com.panda.core.entity;

import com.panda.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class PandaApply extends BaseEntity {

    private Long applicantId;

    private Integer applyType;

    private Integer applyState;
}
