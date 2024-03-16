package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
