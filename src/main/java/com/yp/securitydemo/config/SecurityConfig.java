package com.yp.securitydemo.config;

import com.yp.securitydemo.config.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 自定义登录认证逻辑
     */
    @Autowired
    private CustomLoginAuthenticationProvider customLoginAuthenticationProvider;

    /**
     * 自定义登录认证成功
     */
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    /**
     * 自定义登录认证失败
     */
    @Autowired
    private CustomLoginFailureHandler customLoginFailureHandler;

    /**
     * 自定义无权限逻辑
     */
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    /**
     * 自定义未登录逻辑 或者是 认证授权未通过逻辑
     */
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    /**
     * 自定义登出逻辑
     */
    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    /**
     * JWT认证过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // 配置登录接口
                .loginProcessingUrl("/user/login")
                // 配置登录成功处理
                .successHandler(customLoginSuccessHandler)
                // 配置登录失败处理
                .failureHandler(customLoginFailureHandler);

        http.logout()
                // 配置登出接口
                .logoutUrl("/user/logout")
                // 配置登出处理
                .logoutSuccessHandler(customLogoutSuccessHandler);

        http.exceptionHandling()
                // 配置未登录 或者 认证授权未通过处理
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                // 配置权限不足处理
                .accessDeniedHandler(customAccessDeniedHandler);

        http.authorizeRequests()
                .antMatchers("/test/success").permitAll()
                .anyRequest().authenticated();

        // 基于Token不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 开启跨域
        http.cors();

        // 关闭跨站请求伪造防护
        http.csrf().disable();

        // 把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 配置登录验证逻辑
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置使用自己的自定义登录认证逻辑
        auth.authenticationProvider(customLoginAuthenticationProvider);
    }

    /**
     * 加密方式
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
