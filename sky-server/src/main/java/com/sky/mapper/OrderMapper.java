package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.entity.SalesTop10;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    /**
     * @Description: 根据用户id查询所有的订单
     * @Param: [userId]
     * @return: java.util.List<java.lang.Long>
     */
    List<Orders> getOrdersByUserId(Long userId, Long status);

    /**
     * @Description: 根据id查询订单
     * @Param: [id]
     * @return: void
     */
    @Select("select * from orders where id = #{id};")
    Orders getOrderById(Long id);

    /**
     * @Description: 管理端查询订单
     * @Param: [ordersPageQueryDTO]
     * @return: com.github.pagehelper.Page<com.sky.vo.OrderVO>
     */
    Page<OrderVO> orderSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * @Description: 查询所有订单
     * @Param: []
     * @return: java.util.List<com.sky.entity.Orders>
     */
    @Select("select * from orders")
    List<Orders> getAllOrders();

    /**
     * @Description: 根据订单状态及下单时间查询订单
     * @Param: [status, orderTime]
     * @return: java.util.List<com.sky.entity.Orders>
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTime(Integer status, LocalDateTime orderTime);

    /**
     * @Description: 获取指定状态的所有订单的总金额
     * @Param: [date, status]
     * @return: java.lang.Double
     */
    Double getAmount(LocalDate date, Integer status);

    /** 
     * @Description: 获取指定时间区间内的指定状态的订单数
     * @Param: [date]
     * @return: java.lang.Integer
     */
    Integer getOrdersNumberByDate(LocalDateTime begin, LocalDateTime end, Integer status);

    /**
     * @Description: 获取指定时间区间内的有效订单数
     * @Param: [date]
     * @return: java.lang.Integer
     */
    Integer getValidOrdersByDate(LocalDateTime begin, LocalDateTime end);

    /**
     * @Description: 获取指定时间区间内的菜品销量前十数据
     * @Param: [begin, end]
     * @return: java.util.List<com.sky.entity.SalesTop10>
     */
    List<SalesTop10> getSalesStaticByDate(LocalDateTime begin, LocalDateTime end);
}
