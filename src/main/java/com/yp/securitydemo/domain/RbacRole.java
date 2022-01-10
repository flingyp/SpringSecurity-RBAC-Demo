package com.yp.securitydemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RbacRole implements Serializable {
    private Integer id;
    private String role;
    private String roleName;
}
