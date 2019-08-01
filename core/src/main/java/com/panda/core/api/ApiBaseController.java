package com.panda.core.api;

/**
 * com.panda.core.api.ApiBaseController
 * <p>
 * DATE 2019/7/24
 *
 * @author zhanglijian.
 */
public class ApiBaseController {

    protected String[] profiles(String profile) {
        return profile.split(",");
    }

}
