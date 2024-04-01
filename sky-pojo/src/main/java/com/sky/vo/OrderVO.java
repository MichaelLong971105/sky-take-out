package com.sky.vo;

import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO extends Orders implements Serializable {

    //订单菜品信息
    private String orderDishes;

    //订单详情
    private List<OrderDetail> orderDetailList;

    public String getAllOrderDishes() {
        StringBuilder allDishes = new StringBuilder();
        for (OrderDetail orderDetail : this.orderDetailList) {
            allDishes.append(orderDetail.getName() + "* " + orderDetail.getNumber() + "；");
        }
        return allDishes.toString();
    }

    public void autoSetOrderDishes() {
        this.orderDishes = getAllOrderDishes();
    }
}
