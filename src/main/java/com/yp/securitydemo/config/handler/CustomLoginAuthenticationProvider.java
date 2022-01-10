package com.yp.securitydemo.config.handler;

import com.yp.securitydemo.domain.CustomUserPojo;
import com.yp.securitydemo.domain.RbacMenu;
import com.yp.securitydemo.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 自定义认证逻辑
 */
@Component
public class CustomLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailSevice customUserDetailSevice;

    @Autowired
    private UserSecurityService userSecurityService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1. 获取表单输入中获取用户输入的用户名和密码
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        CustomUserPojo userDetails = (CustomUserPojo) customUserDetailSevice.loadUserByUsername(userName);

        // 2. 比对从数据库查询到的用户的密码 和 用户输入的密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        // 3. 设置用户的权限
        ArrayList<String> authorities = new ArrayList<>();
        List<RbacMenu> menus = userSecurityService.getMenuByUserId(userDetails.getId());
        for(RbacMenu menu : menus) {
            authorities.add(menu.getMenu());
        }
        userDetails.setPermissions(authorities);

        return new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
    }

    /**
     * 这里需要改成 true
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
