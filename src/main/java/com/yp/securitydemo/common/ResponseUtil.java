package com.yp.securitydemo.common;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共返回结果集 - ResponseUtil
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUtil<T> {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 信息提示
     */
    private String msg;
    /**
     * 接口返回的数据
     */
    private T data;

    /**
     * 默认状态码（200）和信息提示（操作成功）
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseUtil success(T data) {
        return new ResponseUtil<T>(200,"操作成功", data);
    }

    /**
     * 默认状态码（500）和信息提示（操作失败）
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseUtil fail(T data) {
        return new ResponseUtil<T>(500,"操作失败", data);
    }

    /**
     * 自定义状态码和信息提示
     * @param code
     * @param msg
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseUtil responseJson(Integer code, String msg, T data) {
        return new ResponseUtil<T>(code, msg, data);
    }

    /**
     * 通过 HttpServletResponse 响应JSON数据
     * @param response
     * @param resultMap
     */
    public static void responseJsonByServlet(HttpServletResponse response, ResponseUtil resultMap) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String jsonString = JSON.toJSONString(resultMap);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(jsonString.getBytes("UTF-8"));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
