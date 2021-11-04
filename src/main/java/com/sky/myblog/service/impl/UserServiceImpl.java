package com.sky.myblog.service.impl;

import com.sky.myblog.entity.User;
import com.sky.myblog.mapper.UserMapper;
import com.sky.myblog.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
