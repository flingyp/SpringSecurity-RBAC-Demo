package com.yp.securitydemo.config.handler;

import com.yp.securitydemo.common.JwtUtil;
import com.yp.securitydemo.common.RedisUtils;
import com.yp.securitydemo.common.ResponseUtil;
import com.yp.securitydemo.domain.CustomUserPojo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 1. 清除Redis中的 用户相关user_id key
        String token = request.getHeader("access-token");
        try {
            // 2. 解析 token
            Integer userId = null;
            Claims claims = JwtUtil.parseJwtToken(token);
            userId = Integer.valueOf(claims.getId()); // 这是用户ID

            // 3. 从redis中删除用户权限
            String redisKey = "user_" + userId;
            boolean deleteRedisKey = redisUtils.deleteObject(redisKey);
            if (deleteRedisKey) {
                // 4. 返回登出成功JSON数据格式
                ResponseUtil responseJson = ResponseUtil.success("退出成功");
                ResponseUtil.responseJsonByServlet(response, responseJson);
            } else {
                ResponseUtil responseJson = ResponseUtil.fail("请勿反复登出");
                ResponseUtil.responseJsonByServlet(response, responseJson);
            }
        } catch (Exception e) {
            System.out.println(e);
            ResponseUtil responseJson = ResponseUtil.fail("登出失败");
            ResponseUtil.responseJsonByServlet(response, responseJson);
        }
    }
}
