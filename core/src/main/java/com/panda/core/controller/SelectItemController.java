package com.panda.core.controller;

import com.panda.common.dto.SelectItemDto;
import com.panda.common.util.SelectItemUtil;
import com.panda.core.consts.CoreConst;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.panda.core.controller.SelectItemControler
 * <p>
 * DATE 2019/6/24
 *
 * @author zhanglijian.
 */
@RestController
@RequestMapping(CoreConst.MAIN_REQUEST_PREFIX + "selectItem")
public class SelectItemController {


    @GetMapping("/static/{type}")
    public List<SelectItemDto> staticSelectItems(
            @PathVariable("type") String type,
            @RequestParam(value = "all", required = false, defaultValue = "false") Boolean all) {

        return SelectItemUtil.selectItem(type, all);
    }
}
