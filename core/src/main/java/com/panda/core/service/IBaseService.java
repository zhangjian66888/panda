package com.panda.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.panda.common.dto.BaseDto;
import com.panda.common.dto.BaseSo;
import com.panda.common.dto.PageDto;
import com.panda.common.entity.BaseEntity;

import java.util.List;
import java.util.Set;

/**
 * com.panda.core.service.IBaseService
 * <p>
 * DATE 2019/6/21
 *
 * @author zhanglijian.
 */
public interface IBaseService<E extends BaseEntity, D extends BaseDto, S extends BaseSo> extends IService<E> {

    PageDto<D> search(S search);

    D findById(Long id);

    boolean insertOrUpdate(D dto);

    boolean deleteById(Long id);

    boolean insertBatch(List<D> dtos);

    List<D> findAll();

    List<D> find(D dto);

    List<Long> findIds(D dto);

    D findOne(D dto);

    List<D> findListByIds(List<Long> ids);

    List<D> findListByIds(Set<Long> ids);

    String[] findListByIdsField();
}
