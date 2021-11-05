package com.sky.myblog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.myblog.common.lang.Result;
import com.sky.myblog.entity.Blog;
import com.sky.myblog.entity.User;
import com.sky.myblog.service.BlogService;
import com.sky.myblog.service.UserService;
import com.sky.myblog.util.ShiroUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sky
 * @since 2021-11-04
 */
@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    UserService userService;

    @GetMapping("/blogs")
    public Result blogs(Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.success(pageData);
    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "This blog has been deleted!");
        return Result.success(blog);
    }

    @RequiresAuthentication
    @PostMapping("/blogs")
    public Result edit(@Validated @RequestBody Blog blog) {
        Blog editedBlog = null;
        if (blog.getId() != null) {
            editedBlog = blogService.getById(blog.getId());
            // only allowed edit blog by author
            System.out.println(ShiroUtil.getAccountProfile().getId());
            Assert.isTrue(editedBlog.getUserId().longValue() == ShiroUtil.getAccountProfile().getId().longValue(),
                    "no access to edit this blog!");
        } else {
            editedBlog = new Blog();
            editedBlog.setUserId(ShiroUtil.getAccountProfile().getId());
            editedBlog.setCreated(LocalDateTime.now());
            editedBlog.setStatus(0);
        }

        BeanUtil.copyProperties(blog, editedBlog, "id", "userId", "created", "status");
        blogService.saveOrUpdate(editedBlog);
        return Result.success(null);
    }

    @RequiresAuthentication
    @PutMapping("/blogs")
    public Result addBlog(@Validated @RequestBody Blog blog) {
        User currentUser = userService.getById(ShiroUtil.getAccountProfile().getId());
        blog.setUserId(currentUser.getId()).setStatus(0).setCreated(LocalDateTime.now());
        blogService.save(blog);
        return Result.success(null);
    }

    @RequiresAuthentication
    @DeleteMapping("/blogs")
    public Result deleteBlog(Integer blogId) {
        User currentUser = userService.getById(ShiroUtil.getAccountProfile().getId());
        Blog blogInfo = blogService.getById(blogId);
        // userId not matched
        Assert.isTrue(blogInfo.getUserId().equals(currentUser.getId()), "no access to delete this blog!");
        blogService.removeById(blogId);
        return Result.success(null);
    }
}
