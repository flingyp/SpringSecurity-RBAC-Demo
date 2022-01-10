package com.yp.securitydemo.dao;

import com.yp.securitydemo.domain.RbacMenu;
import com.yp.securitydemo.domain.RbacUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserSecurityDao {
    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    RbacUser getUserByUserName(String username);

    /**
     * 根据用户ID获取用户权限
     * @param userId
     * @return
     */
    List<RbacMenu> getMenuByUserId(Integer userId);
}
