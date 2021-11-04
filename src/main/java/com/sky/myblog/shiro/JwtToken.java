package com.sky.myblog.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * set shiro token as jwt token
 * @author sky
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
