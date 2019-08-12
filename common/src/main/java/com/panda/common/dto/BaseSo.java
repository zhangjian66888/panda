package com.panda.common.dto;

import com.google.common.collect.Lists;
import com.panda.common.consts.CommonConst;
import com.panda.common.mybatis.InPair;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * com.panda.common.dto.BaseSearchDto
 * <p>
 * DATE 2019/6/3
 *
 * @author zhanglijian.
 */
@Getter
@Setter
public abstract class BaseSo {

    protected Long id;
    private Integer delState;

    protected String sortField;
    protected String sortOrder;

    protected long current;
    protected long pageSize = CommonConst.DEFAULT_PAGE_SIZE;

    protected List<Long> ids;

    protected List<InPair> ins;


    public List<InPair> getIns() {
        this.ins = Optional.ofNullable(ins).orElse(Lists.newArrayList());
        return ins;
    }

    public BaseSo in(String column, List list) {
        if (Objects.nonNull(list) && !list.isEmpty()) {
            getIns().add(InPair.builder().column(column).values(list).build());
        }
        return this;
    }

}
