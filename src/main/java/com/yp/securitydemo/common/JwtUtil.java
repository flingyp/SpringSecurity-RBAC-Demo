package com.yp.securitydemo.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;


public class JwtUtil {
    /**
     * 过期时间 （一天）
     */
    private static final Integer timeOutTime = 1000 * 60 * 60 * 24;

    /**
     * 密钥
     */
    private static final String secrt = "JWT-UTIL";

    /**
     * 生成 token
     * @param jtiId   标识ID
     * @param subName 用户主体名
     * @return
     */
    public static String createJwtToken(Integer jtiId, String subName) {
        JwtBuilder jwtBuilder = Jwts.builder()
                // 声明的标识 {"jti":"888"}
                .setId(jtiId.toString())
                // 主体，用户{"sub":"Rose"}
                .setSubject(subName)
                // 创建日期 {"ita":"xxxxxx"}
                .setIssuedAt(new Date())
                // 设置过期时间 {"exp": "xxxx"}
                .setExpiration(new Date(System.currentTimeMillis() + timeOutTime))
                // 签名手段，参数1：算法，参数2：盐
                .signWith(SignatureAlgorithm.HS256, createEncodeSecretKey());

        // 根据上面的规则生成一个Token
        return jwtBuilder.compact();
    }

    /**
     * 解析 token
     * @param jwtToken
     * @return
     */
    public static Claims parseJwtToken(String jwtToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(createEncodeSecretKey())
                .parseClaimsJws(jwtToken)
                .getBody();

        return claims;
    }

    /**
     * 通过 Base64 生成一个简单编码后的秘钥
     * @return
     */
    private static String createEncodeSecretKey() {
        String keyEncodedToBase64String = null;
        try {
            keyEncodedToBase64String = Base64.getEncoder().encodeToString(secrt.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return keyEncodedToBase64String;
    }
}
