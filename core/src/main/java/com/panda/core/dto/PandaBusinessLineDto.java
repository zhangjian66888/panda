package com.panda.core.dto;

import com.panda.common.dto.BaseDto;
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
public class PandaBusinessLineDto extends BaseDto {

    private String businessLineName;

    private Integer businessLineLevel;

    private String businessLineDescribe;

    private Long parentId;
}
