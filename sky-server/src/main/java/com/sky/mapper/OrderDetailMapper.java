package com.sky.mapper;

import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description:
 * @author: MichaelLong
 * @create: 2024-03-22 23:10
 **/

@Mapper
public interface OrderDetailMapper {

    /**
     * @Description: 批量插入订单明细
     * @Param: [orderDetails]
     * @return: void
     */
    void insertBatch(List<OrderDetail> orderDetails);

    /**
     * @Description: 根据查询orders的number查询所有的订单详细
     * @Param: [orders]
     * @return: java.util.List<com.sky.entity.OrderDetail>
     */
    @Select("select * from order_detail where order_id = #{orderId}")
    List<OrderDetail> getByOrderId(Long orderId);
}
