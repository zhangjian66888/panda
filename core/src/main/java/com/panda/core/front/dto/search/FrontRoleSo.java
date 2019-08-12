package com.panda.core.front.dto.search;

import com.panda.common.dto.BaseSo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * com.panda.core.front.dto.search.FrontRoleSo
 * <p>
 * DATE 2019/8/9
 *
 * @author zhanglijian.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FrontRoleSo extends BaseSo {

    private List<Long> envCodes;
    private List<Long> businessLineIds;
    private List<Long> appCodes;

}
