package com.sky.myblog.shiro;

import lombok.Data;

import java.io.Serializable;
/**
 * After success login, return some information about the user.
 * @author sky
 */
@Data
public class AccountProfile implements Serializable {
    private Long id;
    private String username;
    private String avatar;
}
