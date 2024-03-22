package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

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
}
