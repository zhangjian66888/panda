package com.panda.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * com.panda.core.dto.LoginSuccessDto
 * <p>
 * DATE 2019/7/4
 *
 * @author zhanglijian.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginSuccessDto {

    private String accessToken;

    private LocalDateTime expireTime;

    private String zhName;

    private String timeCode;

    private String redirectUrl;

}
