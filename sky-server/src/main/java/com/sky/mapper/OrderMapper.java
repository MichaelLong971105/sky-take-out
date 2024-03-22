package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: sky-take-out-backend
 * @description: 用户下单相关Mapper
 * @author: MichaelLong
 * @create: 2024-03-22 23:09
 **/

@Mapper
public interface OrderMapper {

    /**
     * @Description: 插入订单数据
     * @Param: [orders]
     * @return: void
     */
    void insert(Orders orders);
}
