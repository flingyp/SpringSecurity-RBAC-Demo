package com.yp.securitydemo.config.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yp.securitydemo.common.JwtUtil;
import com.yp.securitydemo.common.RedisUtils;
import com.yp.securitydemo.domain.CustomUserPojo;
import com.yp.securitydemo.domain.RbacMenu;
import com.yp.securitydemo.service.UserSecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // System.out.println("JWT过滤器");
        // filterChain.doFilter(request,response);

        // 1. 获取 token
        String token = request.getHeader("access-token");
        if (token != null) {
            try {
                // 2. 解析 token
                Integer userId = null;
                Claims claims = JwtUtil.parseJwtToken(token);
                userId = Integer.valueOf(claims.getId()); // 这是用户ID


                // 3. 从redis中获取用户权限
                String redisKey = "user_" + userId;
                ArrayList<String> rbacMenus = redisUtils.getCacheObject(redisKey);
                CustomUserPojo userPojo = new CustomUserPojo();
                userPojo.setId(userId);
                userPojo.setPermissions(rbacMenus);

                if (Objects.isNull(userPojo)) {
                    throw new RuntimeException("用户未登录");
                }

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userPojo, null, userPojo.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        filterChain.doFilter(request, response);
        return;
    }
}
