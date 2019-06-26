package com.panda.core.dto;

import com.panda.common.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.panda.core.dto.PandaAppDto
 * <p>
 * DATE 2019/6/4
 *
 * @author zhanglijian.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PandaAppDto extends BaseDto {

    private String appName;
    private String appAlias;
    private Integer appCode;
    private Integer appLevel;
    private Long businessLineId;
    private String businessLineName;

}
