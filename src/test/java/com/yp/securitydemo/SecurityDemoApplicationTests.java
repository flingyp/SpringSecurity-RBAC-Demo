package com.yp.securitydemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yp.securitydemo.common.RedisUtils;
import com.yp.securitydemo.domain.RbacMenu;
import com.yp.securitydemo.service.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SecurityDemoApplicationTests {
    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private RedisUtils redisUtils;

    @Test
    void test() {
        List<RbacMenu> menus = userSecurityService.getMenuByUserId(1);
        System.out.println(menus);
    }

}
