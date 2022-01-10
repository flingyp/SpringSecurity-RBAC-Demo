package com.yp.securitydemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RbacMenu implements Serializable {
    private Integer id;
    private String menu;
    private String menuName;
}
