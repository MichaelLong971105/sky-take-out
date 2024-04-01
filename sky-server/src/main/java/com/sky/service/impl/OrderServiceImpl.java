package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.service.ShoppingCartService;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description: 用户下单Service功能实现
 * @author: MichaelLong
 * @create: 2024-03-22 23:07
 **/

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    /**
     * @Description: 用户下单功能实现
     * @Param: [ordersSubmitDTO]
     * @return: com.sky.vo.OrderSubmitVO
     */
    @Override
    @Transactional
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {

        //1.处理各种业务异常(地址簿为空、购物车数据为空)
        //查询当前用户地址簿信息
        Long addressBookId = ordersSubmitDTO.getAddressBookId();
        AddressBook addressBook = addressBookMapper.getById(addressBookId);
        if (addressBook == null) {
            //当前用户地址簿为空，抛出异常
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        //查询当前用户购物车信息
        ShoppingCart shoppingCart = new ShoppingCart();
        Long currentUserId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentUserId);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        if (shoppingCartList == null || shoppingCartList.isEmpty()) {
            //当前用户购物车数据为空，抛出异常
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        //2.向订单表中插入1条数据
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setAddress(addressBook.getFullAddress());
        orders.setUserId(currentUserId);

        orderMapper.insert(orders);

        //3.向订单明细表中插入n条数据
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(orders.getId()); //设置当前订单明细关联的订单id
            orderDetails.add(orderDetail);
        }
        orderDetailMapper.insertBatch(orderDetails);

        //4.清空用户购物车数据
        shoppingCartMapper.clean(currentUserId);

        //5.封装VO返回结果
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .orderTime(orders.getOrderTime())
                .orderAmount(orders.getAmount())
                .orderNumber(orders.getNumber())
                .build();

        return orderSubmitVO;
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        //调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getOrderNumber(), //商户订单号
                new BigDecimal(0.01), //支付金额，单位 元
                "苍穹外卖订单", //商品描述
                user.getOpenid() //微信用户的openid
        );

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {

        // 根据订单号查询订单
        Orders ordersDB = orderMapper.getByNumber(outTradeNo);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);
    }

    /**
     * @Description: 查询历史订单
     * @Param: [ordersPageQueryDTO, status]
     * @return: com.sky.vo.OrderVO
     */
    @Override
    public PageResult historyOrders(OrdersPageQueryDTO ordersPageQueryDTO, Long status) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());

        Page<OrderVO> page = new Page<>();

        //从ordersPageQueryDTO中获取到userId
        Long userId = BaseContext.getCurrentId();

        //根据userId查询该用户的所有符合status的订单
        List<Orders> ordersList = orderMapper.getOrdersByUserId(userId, status);

        //遍历该用户的所有订单，并获得该订单的orderDetail
        for (Orders order : ordersList) {
            Long orderId = order.getId();
            List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orderId);
            //封装数据到OrderVO对象中并添加到OrderVO集合中
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            orderVO.setOrderDetailList(orderDetailList);
            page.add(orderVO);
        }

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * @Description: 根据id查询订单详情
     * @Param: [id]
     * @return: com.sky.vo.OrderVO
     */
    @Override
    public OrderVO orderDetail(Long id) {
        OrderVO orderVO = new OrderVO();
        Orders order = orderMapper.getOrderById(id);
        List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(id);
        BeanUtils.copyProperties(order, orderVO);
        orderVO.setOrderDetailList(orderDetails);
        return orderVO;
    }

    /**
     * @Description: 取消订单
     * @Param: [id]
     * @return: void
     */
    @Override
    public void cancelOrder(Long id) {
        Orders order = orderMapper.getOrderById(id);
        order.setStatus(Orders.CANCELLED);
        order.setCancelTime(LocalDateTime.now());
        order.setCancelReason("用户取消");
        orderMapper.update(order);
    }

    /**
     * @Description: 再来一单
     * @Param: [id]
     * @return: void
     */
    @Override
    @Transactional
    public void orderRepetition(Long id) {
        //获取旧订单详情及用户id
        List<OrderDetail> oldOrderDetails = orderDetailMapper.getByOrderId(id);
        Long userId = BaseContext.getCurrentId();

        //创建新的购物车列表
        List<ShoppingCart> shoppingCartList = new ArrayList<>();

        //遍历旧订单详情,生成购物车商品,加入购物车列表
        for (OrderDetail oldOrderDetail : oldOrderDetails) {
            ShoppingCart shoppingCart = ShoppingCart.builder()
                    .userId(userId)
                    .createTime(LocalDateTime.now()).build();
            BeanUtils.copyProperties(oldOrderDetail, shoppingCart);
            shoppingCartList.add(shoppingCart);
        }

        shoppingCartMapper.insertOldOrder(shoppingCartList);
    }

    /**
     * @Description: 订单搜索
     * @Param: [ordersPageQueryDTO]
     * @return: com.sky.result.PageResult
     */
    @Override
    public PageResult orderSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
        Page<OrderVO> page = orderMapper.orderSearch(ordersPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * @Description: 各个状态订单数量统计
     * @Param: []
     * @return: com.sky.vo.OrderStatisticsVO
     */
    @Override
    public OrderStatisticsVO orderStatic() {
        List<Orders> ordersList = orderMapper.getAllOrders();
        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        int toBeConfirmed = 0, confirmed = 0, deliveryInProgress = 0;
        for (Orders orders : ordersList) {
            if (orders.getStatus().equals(2)) {
                toBeConfirmed++;
            } else if (orders.getStatus().equals(3)) {
                confirmed++;
            } else if (orders.getStatus().equals(4)) {
                deliveryInProgress++;
            }
        }
        orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
        orderStatisticsVO.setConfirmed(confirmed);
        orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);
        return orderStatisticsVO;
    }
}
