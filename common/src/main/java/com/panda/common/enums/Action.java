package com.panda.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Description
 * <p>
 * DATE 2018/4/16.
 *
 * @author zhanglijian.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Action {

    DEFAULT(0, "default"),
    LIST(1, "list"),
    TOEDIT(2, "toEdit"),
    TODETAIL(3, "toDetail"),
    DELETE(4, "delete"),
    RUN(5, "run"),
    CLEAR(6, "clear"),
    RESET(7, "reset"),
    ACTIVE(8, "active"),
    LOCK(9, "lock"),
    INIT(10, "init"),
    TOOWNER(11, "toOwner"),
    LOG(12, "log"),
    PROPERTY(13, "property"),
    CHECK(14, "check"),
    RELOAD(15, "reload"),
    COPY(16, "copy"),
    RESTART(17, "restart"),
    STOP(18, "stop"),
    FORCEBRANCH(19, "forceBranch"),
    UPDATE(20, "update"),
    MASTER(21, "master"),
    SHOW(22, "show");

    private int id;
    private String value;
}
