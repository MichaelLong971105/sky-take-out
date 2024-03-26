package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);
}
