package com.panda.core.service;

import com.panda.common.dto.BaseSo;
import com.panda.common.dto.SelectItemDto;

import java.util.List;

/**
 * com.panda.core.service.ISelectItemAble
 * <p>
 * DATE 2019/6/28
 *
 * @author zhanglijian.
 */
public interface ISelectItem<S extends BaseSo> {

    List<SelectItemDto> selectItem(boolean all, S s);

    List<SelectItemDto> selectItem(boolean all);

    String[] selectItemField();
}
