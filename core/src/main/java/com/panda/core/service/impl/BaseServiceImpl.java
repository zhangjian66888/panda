package com.panda.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.panda.common.dto.BaseDto;
import com.panda.common.dto.BaseSo;
import com.panda.common.dto.PageDto;
import com.panda.common.dto.SelectItemDto;
import com.panda.common.entity.BaseEntity;
import com.panda.common.enums.DelState;
import com.panda.common.enums.SortOrder;
import com.panda.common.mybatis.InPair;
import com.panda.common.util.BeanUtil;
import com.panda.common.util.ClassUtil;
import com.panda.common.util.ParseUtils;
import com.panda.core.service.IBaseService;
import com.panda.core.service.ISelectItem;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * com.panda.core.service.impl.BaseServiceImpl
 * <p>
 * DATE 2019/6/21
 *
 * @author zhanglijian.
 */
public class BaseServiceImpl<M extends BaseMapper<E>, E extends BaseEntity, D extends BaseDto, S extends BaseSo>
        extends ServiceImpl<M, E> implements IBaseService<E, D, S>, ISelectItem<S> {


    private Class<E> clzE;
    private Class<D> clzD;
    private Class<S> clzS;

    public BaseServiceImpl() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        clzE = (Class<E>) type.getActualTypeArguments()[1];
        clzD = (Class<D>) type.getActualTypeArguments()[2];
        clzS = (Class<S>) type.getActualTypeArguments()[3];
    }

    @Override
    public PageDto<D> search(S search) {
        Page<E> sPage = new Page<>();
        E query = BeanUtil.transBean(search, clzE);
        query.setDelState(DelState.NO.getId());
        sPage.setCurrent(search.getCurrent());
        sPage.setSize(search.getPageSize());
        if (StringUtils.hasLength(search.getSortField()) && StringUtils.hasLength(search.getSortOrder())) {
            search.setSortField(ParseUtils.camelToUnderline(search.getSortField()));
            if (SortOrder.ASC.getValue().equalsIgnoreCase(search.getSortOrder())) {
                sPage.setAsc(search.getSortField());
            } else {
                sPage.setDesc(search.getSortField());
            }
        } else {
            sPage.setDesc("id");
        }
        QueryWrapper<E> queryWrapper = new QueryWrapper<>(query);
        if (Objects.nonNull(search.getIns()) && !search.getIns().isEmpty()) {
            for (InPair inPair : search.getIns()) {
                queryWrapper.in(inPair.getColumn(), inPair.getValues());
            }
        }
        queryWrapper.nonEmptyOfEntity();
        IPage<E> iPage = super.page(sPage, queryWrapper);

        PageDto<D> pageDto = new PageDto<>();
        pageDto.setCurrent(iPage.getCurrent());
        pageDto.setPageSize(iPage.getSize());
        pageDto.setTotal(iPage.getTotal());
        List<D> records = iPage.getRecords().stream().map(t -> BeanUtil.transBean(t, clzD)).collect(Collectors.toList());
        pageDto.setRecords(records);
        return pageDto;
    }

    @Override
    public D findById(Long id) {
        return Optional.ofNullable(getById(id)).map(t -> BeanUtil.transBean(t, clzD))
                .orElse(ClassUtil.newInstance(clzD));

    }

    @Override
    public boolean insertOrUpdate(D dto) {
        LocalDateTime now = LocalDateTime.now();
        E e = BeanUtil.transBean(dto, clzE);
        e.setUpdateTime(now);
        if (Objects.isNull(e.getId())) {
            e.setCreateTime(now);
        }
        return saveOrUpdate(e);
    }

    @Override
    public boolean deleteById(Long id) {
        E update = ClassUtil.newInstance(clzE);
        update.setId(id);
        update.setDelState(DelState.YES.getId());
        return updateById(update);

    }

    @Override
    public boolean insertBatch(List<D> dtos) {
        LocalDateTime now = LocalDateTime.now();
        List<E> list = Lists.newArrayList();
        for (D d : dtos) {
            E e = BeanUtil.transBean(d, clzE);
            e.setUpdateTime(now);
            e.setCreateTime(now);
            list.add(e);
        }
        return saveBatch(list);
    }

    @Override
    public List<D> findAll() {
        D query = ClassUtil.newInstance(clzD);
        return find(query);
    }

    @Override
    public List<D> find(D dto) {
        List<E> list = finding(dto);
        return Optional.ofNullable(list).orElse(Lists.newArrayList())
                .stream().map(t -> BeanUtil.transBean(t, clzD)).collect(Collectors.toList());

    }

    @Override
    public List<Long> findIds(D dto) {
        List<E> list = finding(dto, "id");
        return Optional.ofNullable(list).orElse(Lists.newArrayList())
                .stream().map(t -> t.getId()).collect(Collectors.toList());
    }

    public List<E> finding(D dto, String... select) {
        E query = BeanUtil.transBean(dto, clzE);
        query.setDelState(DelState.NO.getId());
        QueryWrapper<E> queryWrapper = new QueryWrapper<>(query);
        if (Objects.nonNull(select) && select.length > 0) {
            queryWrapper.select(select);
        }
        queryWrapper.nonEmptyOfEntity();
        List<E> list = super.list(queryWrapper);
        return list;
    }

    @Override
    public D findOne(D dto) {
        return find(dto).stream().findFirst().orElse(null);
    }

    @Override
    public List<D> findListByIds(List<Long> ids) {
        E query = ClassUtil.newInstance(clzE);
        query.setDelState(DelState.NO.getId());
        QueryWrapper<E> queryWrapper = new QueryWrapper<>(query);
        queryWrapper.in("id", ids);
        queryWrapper.select(findListByIdsField());
        queryWrapper.nonEmptyOfEntity();
        List<E> list = super.list(queryWrapper);
        return Optional.ofNullable(list).orElse(Lists.newArrayList())
                .stream().map(t -> BeanUtil.transBean(t, clzD)).collect(Collectors.toList());

    }

    @Override
    public List<D> findListByIds(Set<Long> ids) {
        return findListByIds(new ArrayList<>(ids));
    }

    @Override
    public String[] findListByIdsField() {
        return null;
    }

    @Override
    public List<SelectItemDto> selectItem(boolean all) {
        return selectItem(all, ClassUtil.newInstance(clzS));
    }


    @Override
    public List<SelectItemDto> selectItem(boolean all, List<Long> ids) {
        S s = ClassUtil.newInstance(clzS);
        s.setIds(ids);
        return selectItem(all, s);
    }

    @Override
    public List<SelectItemDto> selectItem(boolean all, BaseSo s) {

        E query = null;
        if (Objects.nonNull(s)) {
            query = BeanUtil.transBean(s, clzE);
        } else {
            query = ClassUtil.newInstance(clzE);
        }
        query.setDelState(DelState.NO.getId());
        QueryWrapper<E> queryWrapper = new QueryWrapper<>(query);
        if (Objects.nonNull(s.getIds()) && !s.getIds().isEmpty()) {
            queryWrapper.in("id", s.getIds());
        }
        if (Objects.nonNull(s.getIns()) && !s.getIns().isEmpty()) {
            for (InPair inPair : s.getIns()) {
                queryWrapper.in(inPair.getColumn(), inPair.getValues());
            }
        }
        String[] columns = selectItemField();
        String id = ParseUtils.camelToUnderline(columns[0]);
        String value = ParseUtils.camelToUnderline(columns[1]);
        queryWrapper.select(id, value);

        Method idMethod = ReflectionUtils.findMethod(clzE, "get" + StringUtils.capitalize(columns[0]));
        Method valueMethod = ReflectionUtils.findMethod(clzE, "get" + StringUtils.capitalize(columns[1]));
        List<SelectItemDto> list = Optional.ofNullable(list(queryWrapper)).orElse(Lists.newArrayList())
                .stream()
                .map(t -> {
                    SelectItemDto selectItem = new SelectItemDto();
                    selectItem.setId((Long) ReflectionUtils.invokeMethod(idMethod, t));
                    selectItem.setValue((String) ReflectionUtils.invokeMethod(valueMethod, t));
                    return selectItem;
                })
                .collect(Collectors.toList());
        if (all) {
            list.add(0, SelectItemDto.builder().value("全部").build());
        }
        return list;
    }


    @Override
    public String[] selectItemField() {
        return new String[]{"id", "name"};
    }
}
