package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description: 购物车相关Service
 * @author: MichaelLong
 * @create: 2024-03-18 22:58
 **/
public interface ShoppingCartService {

    /**
     * @Description: 添加商品到购物车
     * @Param: [shoppingCartDTO]
     * @return: void
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * @Description: 根据用户id查看所有购物车数据
     * @Param: []
     * @return: com.sky.entity.ShoppingCart
     */
    List<ShoppingCart> listAll();

    /**
     * @Description: 减少一个购物车中的商品
     * @Param: [shoppingCartDTO]
     * @return: void
     */
    void sub(ShoppingCartDTO shoppingCartDTO);

    /**
     * @Description: 清空购物车
     * @Param: []
     * @return: void
     */
    void clean();
}
