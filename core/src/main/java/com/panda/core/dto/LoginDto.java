package com.panda.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * com.panda.core.dto.LoginDto
 * <p>
 * DATE 2019/7/4
 *
 * @author zhanglijian.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotNull
    private String username;
    @NotNull
    private String password;

    private Integer loginType;

    private String email;
    private String mobile;

    private Long appCode;
    private String profile;
    private String secret;
    private String redirectUrl;

}
