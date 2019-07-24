package com.panda.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * com.panda.api.dto.UserInfo
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {

    private Long userId;

    private String username;

    private String zhName;

    private Set<Long> roleIds;

    private Boolean superman;
}
