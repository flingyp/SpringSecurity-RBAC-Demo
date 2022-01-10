package com.yp.securitydemo.config.handler;

import com.yp.securitydemo.common.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义未登录逻辑 或者是 认证授权未通过逻辑
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil responseJson = ResponseUtil.responseJson(401, "token有误，请重新登录", null);
        ResponseUtil.responseJsonByServlet(response, responseJson);
    }
}
