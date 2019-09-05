package com.panda.common.dto;

import com.google.common.collect.Lists;
import com.panda.common.mybatis.InPair;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

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

    protected Collection<InPair> ins;

    public Collection<InPair> getIns() {
        this.ins = Optional.ofNullable(ins).orElse(Lists.newArrayList());
        return ins;
    }
    public BaseDto in(String column, Collection collection) {
        if (Objects.nonNull(collection) && !collection.isEmpty()) {
            getIns().add(InPair.builder().column(column).values(collection).build());
        }
        return this;
    }
}
