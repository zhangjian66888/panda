package com.panda.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * com.panda.common.dto.BaseDto
 * <p>
 * DATE 2019/6/3
 *
 * @author zhanglijian.
 */
@Setter
@Getter
public abstract class BaseDto {

    protected Long id;

    private Integer delState;
    protected LocalDateTime createTime;
    protected LocalDateTime updateTime;
}
