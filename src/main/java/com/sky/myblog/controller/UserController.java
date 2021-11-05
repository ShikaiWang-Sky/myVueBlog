package com.sky.myblog.controller;


import com.sky.myblog.common.lang.Result;
import com.sky.myblog.entity.User;
import com.sky.myblog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sky
 * @since 2021-11-04
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * test shiro
     * use @RequireAuthentication to enable authentication using shiro
     * @param id user id
     * @return user's information
     */
    @RequiresAuthentication
    @GetMapping("/{id}")
    public Object test(@PathVariable("id") Long id) {
        User user =  userService.getById(id);
        return Result.success(user);
    }

    @PostMapping("/save")
    public Object testUser(@Validated @RequestBody User user) {
        return user.toString();
    }
}
