package com.panda.core.entity;

import com.panda.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class PandaUser extends BaseEntity {

    private Long businessLineId;

    private String username;

    private Integer userType;

    private String password;

    private String zhName;

    private String mobile;

    private String email;
}
