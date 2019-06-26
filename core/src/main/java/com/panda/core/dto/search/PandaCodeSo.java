package com.panda.core.dto.search;

import com.panda.common.dto.BaseSo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.panda.core.dto.search.PandaCodeSo
 * <p>
 * DATE 2019/6/24
 *
 * @author zhanglijian.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PandaCodeSo extends BaseSo {

    private String codeType;
}
