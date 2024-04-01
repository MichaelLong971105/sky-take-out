package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: sky-take-out-backend
 * @description: 管理端订单管理相关功能
 * @author: MichaelLong
 * @create: 2024-04-01 13:12
 **/

@RestController
@Api(tags = "订单管理功能")
@Slf4j
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @Description: 订单搜索功能Controller
     * @Param: [ordersPageQueryDTO]
     * @return: com.sky.result.Result<com.sky.result.PageResult>
     */
    @GetMapping("/conditionSearch")
    @ApiOperation("订单搜索")
    public Result<PageResult> orderSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("搜索订单:{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.orderSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * @Description: 各个状态订单数量统计
     * @Param: []
     * @return: com.sky.result.Result<com.sky.vo.OrderStatisticsVO>
     */
    @GetMapping("/statistics")
    @ApiOperation("各个状态订单数量统计")
    public Result<OrderStatisticsVO> orderStatic() {
        log.info("各个状态订单数量统计");
        OrderStatisticsVO orderStatisticsVO = orderService.orderStatic();
        return Result.success(orderStatisticsVO);
    }

    /**
     * @Description: 查看订单详情
     * @Param: [id]
     * @return: com.sky.result.Result<com.sky.vo.OrderVO>
     */
    @GetMapping("/details/{id}")
    @ApiOperation("查看订单详情")
    public Result<OrderVO> getOrderDetail(@PathVariable Long id) {
        log.info("查看订单详情{}", id);
        OrderVO orderVO = orderService.orderDetail(id);
        return Result.success(orderVO);
    }

    /**
     * @Description: 商家接单
     * @Param: []
     * @return: com.sky.result.Result
     */
    @PutMapping("/confirm")
    @ApiOperation("商家接单")
    public Result confirmOrder(@RequestBody OrdersConfirmDTO ordersConfirmDTO) {
        log.info("确认接单{}", ordersConfirmDTO.getId());
        orderService.confirmOrder(ordersConfirmDTO);
        return Result.success();
    }

    /**
     * @Description: 商家拒单
     * @Param: [ordersRejectionDTO]
     * @return: com.sky.result.Result
     */
    @PutMapping("/rejection")
    @ApiOperation("商家拒单")
    public Result rejectOrder(@RequestBody OrdersRejectionDTO ordersRejectionDTO) {
        log.info("商家拒单{}", ordersRejectionDTO.getId());
        orderService.rejectOrder(ordersRejectionDTO);
        return Result.success();
    }

    /**
     * @Description: 商家取消订单
     * @Param: [ordersCancelDTO]
     * @return: com.sky.result.Result
     */
    @PutMapping("/cancel")
    @ApiOperation("商家取消订单")
    public Result cancelOrder(@RequestBody OrdersCancelDTO ordersCancelDTO) {
        log.info("取消订单{}", ordersCancelDTO.getId());
        orderService.cancelOrderByShop(ordersCancelDTO);
        return Result.success();
    }

    /**
     * @Description: 派送订单
     * @Param: [id]
     * @return: com.sky.result.Result
     */
    @PutMapping("delivery/{id}")
    @ApiOperation("派送订单")
    public Result deliverOrder(@PathVariable Long id) {
        log.info("派送订单{}", id);
        orderService.deliverOrder(id);
        return Result.success();
    }

    /**
     * @Description: 完成订单
     * @Param: [id]
     * @return: com.sky.result.Result
     */
    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result completeOrder(@PathVariable Long id) {
        log.info("完成订单{}", id);
        orderService.completeOrder(id);
        return Result.success();
    }

}
