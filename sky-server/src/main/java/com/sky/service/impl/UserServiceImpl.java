package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: sky-take-out-backend
 * @description: 用户端操作相关Service实现类
 * @author: MichaelLong
 * @create: 2024-03-16 14:56
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //微信登录接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    /**
     * @Description: 实现微信登录功能
     * @Param: [userLoginDTO]
     * @return: com.sky.entity.User
     */
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {

        //调用微信服务器的登录接口，获得当前微信用户的openid
        String json = getOpenid(userLoginDTO.getCode());

        //从返回的json字符串中解析出openid
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");

        //判断openid是否为空，为空则抛出异常
        if (openid == null) {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //判断当前用户的openid是否在user表中，不在则表示是新用户
        User user = userMapper.getByOpenid(openid);

        //如果是新用户，则完成新用户注册
        if (user == null) {
            //新用户
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            userMapper.addUser(user);
        }

        //返回User对象
        return user;
    }

    /**
     * @Description: 调用微信接口服务获取微信用户的openid
     * @Param: [code]
     * @return: java.lang.String
     */
    private String getOpenid(String code) {
        Map<String, String> wxLoginMap = new HashMap<>();
        wxLoginMap.put("appid", weChatProperties.getAppid());
        wxLoginMap.put("secret", weChatProperties.getSecret());
        wxLoginMap.put("js_code", code);
        wxLoginMap.put("grant_type", "authorization_code");
        return HttpClientUtil.doGet(WX_LOGIN, wxLoginMap);
    }
}
