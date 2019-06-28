package com.panda.common.util;

import com.google.common.collect.Lists;
import com.panda.common.dto.SelectItemDto;
import com.panda.common.exception.PandaException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * Description
 * <p>
 * DATE 2018/4/16.
 *
 * @author zhanglijian.
 */
public class SelectItemUtil {

    public static List<SelectItemDto> selectItem(String type, boolean all) {
        try {
            Class clazz = Class.forName("com.panda.common.enums.".concat(StringUtils.capitalize(type)));
            return selectItem(clazz, all);
        } catch (Exception e) {
            throw new PandaException(String.format("%s class not found", type), e);
        }
    }

    public static List<SelectItemDto> selectItem(Class clazz) {
        return selectItem(clazz, false);
    }

    public static List<SelectItemDto> selectItem(Class clazz, boolean all) {
        List<SelectItemDto> seleteItemDtos = Lists.newArrayList();
        try {
            if (all) {
                seleteItemDtos.add(SelectItemDto.builder().value("全部").build());
            }
            Object[] values = clazz.getEnumConstants();
            if (Objects.nonNull(values)) {
                Method getId = clazz.getMethod("getId");
                Method getValue = clazz.getMethod("getValue");
                for (Object value : values) {
                    SelectItemDto seleteItem = SelectItemDto.builder()
                            .id(Long.valueOf(getId.invoke(value).toString()))
                            .value((String) getValue.invoke(value))
                            .build();
                    seleteItemDtos.add(seleteItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seleteItemDtos;
    }

    public static String getValueById(long id, Class clazz) {
        return getValueById(id, selectItem(clazz));
    }

    public static <T> T getById(long id, Class<T> clazz) {
        try {
            T[] values = clazz.getEnumConstants();
            if (Objects.nonNull(values)) {
                Method getId = clazz.getMethod("getId");
                for (T value : values) {
                    Integer tmp = (Integer) getId.invoke(value);
                    if (id == tmp) {
                        return value;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getValueById(long id, List<SelectItemDto> selectItemDtos) {
        for (SelectItemDto selectItem : selectItemDtos) {
            if (selectItem.getId() == id) {
                return selectItem.getValue();
            }
        }
        return null;
    }
}
