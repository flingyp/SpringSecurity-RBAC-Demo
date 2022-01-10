package com.yp.securitydemo.config.handler;

import com.yp.securitydemo.domain.CustomUserPojo;
import com.yp.securitydemo.domain.RbacUser;
import com.yp.securitydemo.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailSevice implements UserDetailsService {

    @Autowired
    private UserSecurityService userSecurityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RbacUser user = userSecurityService.getUserByUserName(username);
        if(user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        CustomUserPojo userPojo = new CustomUserPojo();
        userPojo.setId(user.getId());
        userPojo.setUsername(user.getUsername());
        userPojo.setPassword(user.getPassword());
        userPojo.setStatus(user.getStatus());

        return userPojo;
    }
}
