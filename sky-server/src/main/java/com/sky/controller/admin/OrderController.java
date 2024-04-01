package com.sky.controller.admin;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<PageResult> orderSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("搜索订单:{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.orderSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

}
