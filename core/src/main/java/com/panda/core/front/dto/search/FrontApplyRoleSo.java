package com.panda.core.front.dto.search;

import com.panda.common.dto.BaseSo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.panda.core.front.dto.search.FrontApplyRoleSo
 * <p>
 * DATE 2019/9/9
 *
 * @author zhanglijian.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontApplyRoleSo extends BaseSo {

    private Long applicantId;

    private Long appCode;

    private List<Integer> applyStates;

}
