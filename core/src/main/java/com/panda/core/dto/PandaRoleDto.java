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
 * @since 2019-06-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PandaRoleDto extends BaseDto {

    private String roleName;

    private Long businessLineId;

    private String businessLineName;

    private Long appCode;

    private String appName;

    private Long envCode;

    private String envName;

    private String description;

}
