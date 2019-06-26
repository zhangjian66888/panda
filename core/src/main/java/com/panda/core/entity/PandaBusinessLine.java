package com.panda.core.entity;

import com.panda.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author zhanglijian
 * @since 2019-06-21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PandaBusinessLine extends BaseEntity {

    private String businessLineName;

    private Integer businessLineLevel;

    private String businessLineDescribe;

    private Long parentId;
}
