package com.yp.securitydemo.controller;

import com.yp.securitydemo.common.ResponseUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/success")
    public ResponseUtil Success() {
        return ResponseUtil.success("成功返回");
    }

    @GetMapping("/fail")
    public ResponseUtil Fail() {
        return ResponseUtil.fail("失败返回");
    }

    @GetMapping("/userMenu")
    @PreAuthorize("hasAuthority('sys:user:*')")
    public ResponseUtil UserMenu() {
        return ResponseUtil.success("UserMenu权限");
    }

    @GetMapping("/roleMenu")
    @PreAuthorize("hasAuthority('sys:role:*')")
    public ResponseUtil RoleMenu() {
        return ResponseUtil.success("RoleMenu权限");
    }

    @GetMapping("/menuMenu")
    @PreAuthorize("hasAuthority('sys:menu:*')")
    public ResponseUtil MenuMenu() {
        return ResponseUtil.success("MenuMenu权限");
    }
}
