package com.panda.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * com.panda.common.entity.BaseEntity
 * <p>
 * DATE 2019/6/3
 *
 * @author zhanglijian.
 */
@Getter
@Setter
public abstract class BaseEntity {

    @TableId(type = IdType.AUTO)
    protected Long id;


    protected Integer delState;
    protected LocalDateTime createTime;
    protected LocalDateTime updateTime;

}
