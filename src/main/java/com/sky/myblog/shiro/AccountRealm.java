package com.sky.myblog.shiro;

import com.sky.myblog.entity.User;
import com.sky.myblog.service.UserService;
import com.sky.myblog.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author sky
 */
@Slf4j
@Component
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;

    /**
     * shiro默认supports的是UsernamePasswordToken，而我们现在采用了jwt的方式，
     * 所以这里我们自定义一个JwtToken，来完成shiro的supports方法
     * @param token we have set token use jwt in JwtToken file
     * @return 每一个Ream都有一个supports方法，用于检测是否支持此Token
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwt = (JwtToken) authenticationToken;
        log.info("jwt------------->{}", jwt);
        String userId = jwtUtils.
                getClaimByToken((String) jwt.getPrincipal()).
                getSubject();
        User user = userService.getById(Long.valueOf(userId));
        if (user == null) {
            throw new UnknownAccountException("Account not exist!");
        }
        if (user.getStatus() == -1) {
            throw new LockedAccountException("Account is locked!");
        }
        AccountProfile profile = new AccountProfile();
        // copy same properties in user to profile
        BeanUtils.copyProperties(user, profile);
        log.info("profile-------------------->{}", profile.toString());
        // getName => current realm name
        // send to shiro
        return new SimpleAuthenticationInfo(profile, jwt.getCredentials(), getName());
    }
}
