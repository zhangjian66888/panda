package com.panda.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Description
 * <p>
 * DATE 2018/4/16.
 *
 * @author zhanglijian.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectItemDto {

    private long id;
    private String value;

    private boolean selected;

}
