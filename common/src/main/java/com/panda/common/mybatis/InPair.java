package com.panda.common.mybatis;

import lombok.*;

import java.util.Collection;
import java.util.List;

/**
 * com.panda.common.mybatis.InPair
 * <p>
 * DATE 2019/7/3
 *
 * @author zhanglijian.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InPair {
    private String column;

    private Collection values;
}
