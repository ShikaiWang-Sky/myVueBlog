package com.sky.myblog.service.impl;

import com.sky.myblog.entity.Blog;
import com.sky.myblog.mapper.BlogMapper;
import com.sky.myblog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sky
 * @since 2021-11-04
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
