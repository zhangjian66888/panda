package com.panda.core.front.dto.search;

import com.panda.common.dto.BaseSo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.panda.core.front.dto.search.FrontApplySo
 * <p>
 * DATE 2019/9/6
 *
 * @author zhanglijian.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontApplySo extends BaseSo {

    private List<Integer> applyTypes;

    private List<Integer> applyState;
}
