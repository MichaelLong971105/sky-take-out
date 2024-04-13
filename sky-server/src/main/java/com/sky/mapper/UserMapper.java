package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @program: sky-take-out-backend
 * @description: 用户相关Mapper
 * @author: MichaelLong
 * @create: 2024-03-16 15:20
 **/

@Mapper
public interface UserMapper {

    /**
     * @Description: 根据openid查询用户
     * @Param: [openid]
     * @return: com.sky.entity.User
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * @Description: 新增用户
     * @Param: [user]
     * @return: void
     */
    void addUser(User user);

    /**
     * @Description: 根据id获取用户信息
     * @Param: [userId]
     * @return: com.sky.entity.User
     */
    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

    /**
     * @Description: 获取指定时间新注册的用户数
     * @Param: [date]
     * @return: java.lang.Integer
     */
    Integer getUserAmount(LocalDateTime begin, LocalDateTime end);
}
