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
 * @since 2019-07-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PandaAppOwnerDto extends BaseDto {

    private Long appCode;

    private Long ownerId;

    private String ownerName;

    private Integer ownerType;
}
