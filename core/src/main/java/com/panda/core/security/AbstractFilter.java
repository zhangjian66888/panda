package com.panda.core.security;

import com.alibaba.fastjson.JSON;
import com.panda.api.consts.ApiConst;
import com.panda.common.dto.StatusDto;
import com.panda.common.exception.PandaFilterException;
import com.panda.core.config.ACLProperties;
import com.panda.core.consts.CoreConst;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * com.panda.core.security.AbstractFilter
 * <p>
 * DATE 2019/7/8
 *
 * @author zhanglijian.
 */
public abstract class AbstractFilter implements Filter {

    @Autowired
    private ACLProperties aclProperties;

    protected boolean ignoring(HttpServletRequest request) {
        if (request.getRequestURI().startsWith(ApiConst.API_REQUEST_PREFIX)
                || request.getRequestURI().equalsIgnoreCase(CoreConst.MAIN_REQUEST_PREFIX.concat("login"))
                || aclProperties.checkACL(request.getRequestURI())) {
            return true;
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (ignoring(request)) {
            chain.doFilter(request, response);
            return;
        }
        try {
            doingFilter(request, response);
        } catch (PandaFilterException e) {
            handlerException(response, e);
            return;
        }
        chain.doFilter(request, response);
    }

    public abstract void doingFilter(HttpServletRequest request, HttpServletResponse response) throws PandaFilterException;

    private void handlerException(HttpServletResponse response, PandaFilterException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(e.getStatus());
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSON.toJSONString(StatusDto.builder().code(StatusDto.CODE_ERROR).msg(e.getMessage()).build()));
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
