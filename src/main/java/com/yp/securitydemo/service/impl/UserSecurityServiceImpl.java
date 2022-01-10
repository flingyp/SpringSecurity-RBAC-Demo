package com.yp.securitydemo.service.impl;

import com.yp.securitydemo.dao.UserSecurityDao;
import com.yp.securitydemo.domain.RbacMenu;
import com.yp.securitydemo.domain.RbacUser;
import com.yp.securitydemo.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    @Autowired
    private UserSecurityDao userSecurityDao;

    @Override
    public RbacUser getUserByUserName(String username) {
        return userSecurityDao.getUserByUserName(username);
    }

    @Override
    public List<RbacMenu> getMenuByUserId(Integer userId) {
        return userSecurityDao.getMenuByUserId(userId);
    }
}
