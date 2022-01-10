package com.yp.securitydemo.service;

import com.yp.securitydemo.domain.RbacMenu;
import com.yp.securitydemo.domain.RbacUser;

import java.util.List;

public interface UserSecurityService {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    RbacUser getUserByUserName(String username);

    List<RbacMenu> getMenuByUserId(Integer userId);
}
