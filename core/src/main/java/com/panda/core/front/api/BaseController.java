package com.panda.core.front.api;

import com.panda.common.dto.BaseDto;
import com.panda.common.dto.BaseSo;
import com.panda.common.dto.PageDto;
import com.panda.common.dto.StatusDto;
import com.panda.common.entity.BaseEntity;
import com.panda.core.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;

/**
 * com.panda.core.controller.BaseController
 * <p>
 * DATE 2019/6/4
 *
 * @author zhanglijian.
 */
public abstract class BaseController<E extends BaseEntity, D extends BaseDto, S extends BaseSo> {

    @Autowired
    protected IBaseService<E, D, S> iBaseService;

    private Class<E> clzE;
    private Class<D> clzD;
    private Class<S> clzS;

    public BaseController() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clzE = (Class<E>) type.getActualTypeArguments()[0];
        clzD = (Class<D>) type.getActualTypeArguments()[1];
        clzS = (Class<S>) type.getActualTypeArguments()[2];
    }

    @RequestMapping("search")
    public PageDto<D> search(S search) {
        PageDto pageDto = iBaseService.search(search);
        if (Objects.nonNull(pageDto)
                && Objects.nonNull(pageDto.getRecords()) && !pageDto.getRecords().isEmpty()) {
            decorateList(pageDto.getRecords());
        }
        return pageDto;
    }


    @GetMapping("/detail")
    public StatusDto detail(@RequestParam(value = "id") Long id) {
        return StatusDto.SUCCESS().setData(decorateDto(iBaseService.findById(id)));
    }

    protected List<D> decorateList(List<D> list) {
        return list;
    }

    protected D decorateDto(D dto) {
        return dto;
    }

}
