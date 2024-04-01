package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

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

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * @Description: 查询历史订单
     * @Param: [ordersPageQueryDTO, status]
     * @return: com.sky.vo.OrderVO
     */
    PageResult historyOrders(OrdersPageQueryDTO ordersPageQueryDTO, Long status);

    /**
     * @Description: 查询订单详情
     * @Param: [id]
     * @return: com.sky.vo.OrderVO
     */
    OrderVO orderDetail(Long id);

    /**
     * @Description: 根据id取消订单
     * @Param: [id]
     * @return: void
     */
    void cancelOrder(Long id);

    /**
     * @Description: 再来一单
     * @Param: [id]
     * @return: void
     */
    void orderRepetition(Long id);

    /**
     * @Description: 订单搜索
     * @Param: [ordersPageQueryDTO]
     * @return: void
     */
    PageResult orderSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * @Description: 各个状态订单数量统计
     * @Param: []
     * @return: com.sky.vo.OrderStatisticsVO
     */
    OrderStatisticsVO orderStatic();
}
