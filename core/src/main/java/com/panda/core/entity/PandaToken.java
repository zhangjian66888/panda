package com.panda.core.entity;

import com.panda.common.entity.BaseEntity;

import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author zhanglijian
 * @since 2019-07-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PandaToken extends BaseEntity {

    private Long userId;

    private String accessToken;

    private LocalDateTime expireTime;

}
