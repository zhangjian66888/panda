package com.panda.core.security;

import lombok.*;

import java.util.Set;

/**
 * com.panda.core.security.PandaUser
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SecurityUser {

    private Long userId;
    private String username;
    private String password;
    private String zhName;
    private String mobile;
    private String email;

    private Set<Long> roleIds;
    private Boolean superman;

}
