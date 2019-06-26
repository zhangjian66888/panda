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
 * @since 2019-06-24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PandaCodeDto extends BaseDto {

    private String codeType;

    private Integer codeValue;

}
