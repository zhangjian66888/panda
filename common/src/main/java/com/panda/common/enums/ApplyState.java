package com.panda.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * com.panda.common.enums.ApplyState
 * <p>
 * DATE 2019/9/6
 *
 * @author zhanglijian.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ApplyState {

    SUBMIT(0,"已提交"),
    DOING(10,"审批中"),
    PASS(20,"审批通过"),
    BACK(30,"退回"),
    END(50,"审批通过");

    private int id;
    private String label;
}
