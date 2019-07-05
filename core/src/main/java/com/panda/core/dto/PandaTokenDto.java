package com.panda.core.dto;

import com.panda.common.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * com.panda.core.dto.PandaTokenDto
 * <p>
 * DATE 2019/7/4
 *
 * @author zhanglijian.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PandaTokenDto extends BaseDto {

    private Long userId;

    private String accessToken;

    private LocalDateTime expireTime;
}
