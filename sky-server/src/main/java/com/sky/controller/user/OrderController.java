package com.sky.controller.user;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: sky-take-out-backend
 * @description: 用户下单相关Controller
 * @author: MichaelLong
 * @create: 2024-03-22 23:01
 **/

@RestController("userOrderController")
@RequestMapping("/user/order")
@Slf4j
@Api(tags = "用户端订单相关接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @Description: 用户下单相关功能
     * @Param: []
     * @return: com.sky.result.Result<com.sky.vo.OrderSubmitVO>
     */
    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单参数为:{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        orderService.paySuccess(ordersPaymentDTO.getOrderNumber());
        /*
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
        */
        return Result.success();
    }

    /**
     * @Description: 查询历史订单
     * @Param: [ordersPageQueryDTO, status]
     * @return: com.sky.result.Result<com.sky.vo.OrderVO>
     */
    @GetMapping("/historyOrders")
    @ApiOperation("用户查看历史订单")
    public Result<PageResult> historyOrders(OrdersPageQueryDTO ordersPageQueryDTO, Long status) {
        log.info("查看历史订单:{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.historyOrders(ordersPageQueryDTO, status);
        return Result.success(pageResult);
    }

    /**
     * @Description: 查询订单详情
     * @Param: [id]
     * @return: com.sky.result.Result<com.sky.vo.OrderVO>
     */
    @GetMapping("/orderDetail/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> orderDetails(@PathVariable Long id) {
        log.info("查询订单详细:{}", id);
        OrderVO orderVO = orderService.orderDetail(id);
        return Result.success(orderVO);
    }

    /**
     * @Description: 用户取消订单
     * @Param: [id]
     * @return: com.sky.result.Result
     */
    @PutMapping("/cancel/{id}")
    @ApiOperation("用户取消订单")
    public Result cancelOrder(@PathVariable Long id) {
        log.info("用户取消订单:{}", id);
        orderService.cancelOrderByUser(id);
        return Result.success();
    }

    /**
     * @Description: 再来一单
     * @Param: [id]
     * @return: com.sky.result.Result
     */
    @PostMapping("/repetition/{id}")
    @ApiOperation("再来一单")
    public Result orderRepetition(@PathVariable Long id) {
        log.info("再来一单:{}", id);
        orderService.orderRepetition(id);
        return Result.success();
    }

    /**
     * @Description: 客户催单
     * @Param: [id]
     * @return: com.sky.result.Result
     */
    @GetMapping("/reminder/{id}")
    @ApiOperation("客户催单")
    public Result reminder(@PathVariable Long id) {
        log.info("客户催单:{}", id);
        orderService.reminder(id);
        return Result.success();
    }

}
