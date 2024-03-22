package com.sky.service;

import com.sky.dto.OrdersSubmitDTO;
import com.sky.vo.OrderSubmitVO;

/**
 * @program: sky-take-out-backend
 * @description:
 * @author: MichaelLong
 * @create: 2024-03-22 23:05
 **/
public interface OrderService {

    /**
     * @Description: 用户下单功能
     * @Param: [ordersSubmitDTO]
     * @return: com.sky.vo.OrderSubmitVO
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);
}
