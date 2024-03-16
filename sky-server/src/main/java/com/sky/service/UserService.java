package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @program: sky-take-out-backend
 * @description: 用户端操作相关Service
 * @author: MichaelLong
 * @create: 2024-03-16 14:56
 **/
public interface UserService {

    /**
     * @Description: 微信登录功能
     * @Param: [userLoginDTO]
     * @return: com.sky.entity.User
     */
    User wxLogin(UserLoginDTO userLoginDTO);

}
