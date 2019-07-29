package com.panda.client.filter;

import com.alibaba.fastjson.JSON;
import com.panda.api.consts.ApiConst;
import com.panda.client.config.ACLProperties;
import com.panda.client.config.AuthProperties;
import com.panda.client.consts.AuthConst;
import com.panda.common.dto.ResultDto;
import com.panda.common.dto.StatusDto;
import com.panda.common.exception.LoginException;
import com.panda.common.exception.PandaFilterException;
import com.panda.common.exception.PermissionException;
import com.panda.common.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
public abstract class AbstractFilter extends GenericFilterBean {

    @Autowired
    protected ACLProperties aclProperties;

    @Autowired
    protected AuthProperties authProperties;

    protected boolean ignoring(HttpServletRequest request) {
        if (aclProperties.checkACL(request.getRequestURI())) {
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
            if (!doingFilter(request, response)) {
                return;
            }
        } catch (LoginException e) {
            handlerException(request, response, e);
            return;
        } catch (PermissionException e) {
            handlerException(request, response, e);
            return;
        }
        chain.doFilter(request, response);
    }

    public abstract boolean doingFilter(HttpServletRequest request, HttpServletResponse response)
            throws PandaFilterException, IOException;

    private void handlerException(HttpServletRequest request, HttpServletResponse response, LoginException e) throws IOException {
        String url = PathUtil.urlJoin(authProperties.getFrontHost(), ApiConst.LOGIN_PAGE);
        String callback = PathUtil.urlJoin(originHost(request), AuthConst.AUTH_REQUEST_PREFIX, ApiConst.SUCCESS_REQUEST_PREFIX);
        url = url.concat("?callback=").concat(callback);
        url = url.concat("&level=" + authProperties.getLevel());
        if (isAjaxRequest(request)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(e.getStatus());
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.write(JSON.toJSONString(ResultDto.builder().code(StatusDto.CODE_ERROR).msg(e.getMessage()).build().setData(url)));
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } else {
            response.sendRedirect(url);
        }
    }


    private void handlerException(HttpServletRequest request, HttpServletResponse response, PermissionException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(e.getStatus());
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSON.toJSONString(ResultDto.builder().code(StatusDto.CODE_ERROR).msg(e.getMessage()).build()));
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static String originHost(HttpServletRequest request) {
        String host = request.getHeader("Origin");
        if (!StringUtils.hasLength(host)) {
            String referer = request.getHeader("Referer");
            if (StringUtils.hasLength(referer)) {
                String[] tmp = referer.split("://");
                host = tmp[0].concat("://") + tmp[1].split("/")[0];
            } else {
                host = "http://".concat(request.getHeader("host"));
            }
        }
        return host;
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestType)) {
            return true;
        }
        return false;
    }
}
