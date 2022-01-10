package com.yp.securitydemo.config.handler;

import com.alibaba.fastjson.JSON;
import com.yp.securitydemo.common.JwtUtil;
import com.yp.securitydemo.common.RedisUtils;
import com.yp.securitydemo.common.ResponseUtil;
import com.yp.securitydemo.domain.CustomUserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义登录认证成功逻辑
 */
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // System.out.println("权限信息列表："+authentication.getAuthorities());
        // System.out.println("用户身份信息列表："+authentication.getPrincipal());

        // 1. 获取用户信息
        CustomUserPojo customUserPojo = (CustomUserPojo) authentication.getPrincipal();

        // 2. 根据用户ID生成Token
        String token = JwtUtil.createJwtToken(customUserPojo.getId(), customUserPojo.getUsername());

        // 3. 将用户权限存入Redis中 login_userid: menus
        redisUtils.setCacheObject("user_" + customUserPojo.getId(), customUserPojo.getPermissions());

        // 4. 返回登录成功JSON数据格式
        Map<String,Object> resultToken = new HashMap<>();
        resultToken.put("access-token",token);
        ResponseUtil responseJson = ResponseUtil.responseJson(200, "登录成功", resultToken);
        ResponseUtil.responseJsonByServlet(response, responseJson);
    }
}
