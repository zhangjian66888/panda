package com.panda.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * com.panda.core.dto.PasswdChangeDto
 * <p>
 * DATE 2019/9/3
 *
 * @author zhanglijian.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswdChangeDto {

    @NotNull
    private String oldPasswd;
    @NotNull
    private String newPasswd;

}
