package com.panda.core.open.filter;

import com.alibaba.fastjson.JSON;
import com.panda.common.dto.StatusDto;
import com.panda.common.exception.PandaFilterException;
import com.panda.core.consts.CoreConst;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * com.panda.core.filter.BaseFilter
 * <p>
 * DATE 2019/8/1
 *
 * @author zhanglijian.
 */
public abstract class BaseFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getRequestURI().startsWith(CoreConst.MAIN_REQUEST_PREFIX)
                || request.getRequestURI().startsWith(CoreConst.FRONT_REQUEST_PREFIX)) {
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
