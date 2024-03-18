package com.sky.service;

import com.sky.dto.ShoppingCartDTO;

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

}
