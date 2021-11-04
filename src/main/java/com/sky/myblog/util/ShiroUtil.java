package com.sky.myblog.util;

import com.sky.myblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @author sky
 */
public class ShiroUtil {
    public static AccountProfile getAccountProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
