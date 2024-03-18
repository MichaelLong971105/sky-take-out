package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.SetMeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description: 购物车Service相关功能实现
 * @author: MichaelLong
 * @create: 2024-03-18 22:59
 **/

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetMealMapper setMealMapper;

    /**
     * @Description: 添加商品到购物车
     * @Param: [shoppingCartDTO]
     * @return: void
     */
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        //判断当前加入购物车中的商品是否已经存在
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        if (list != null && !list.isEmpty()) {
            //已存在 -> 该商品数量+1
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            //更新购物车中的数据
            shoppingCartMapper.updateNumberById(cart);
        } else {
            //不存在 -> 往购物车中插入1条该商品数据
            //判断当前添加商品是菜品还是套餐
            if (shoppingCartDTO.getDishId() != null) {
                //是菜品
                Dish dish = dishMapper.getDishById(shoppingCartDTO.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                //是套餐
                SetMeal setMeal = setMealMapper.getSetMealById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setMeal.getName());
                shoppingCart.setImage(setMeal.getImage());
                shoppingCart.setAmount(setMeal.getPrice());
            }

            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());

            shoppingCartMapper.insert(shoppingCart);
        }

    }

    /**
     * @Description: 根据用户id查看所有购物车数据
     * @Param: []
     * @return: java.util.List<com.sky.entity.ShoppingCart>
     */
    @Override
    public List<ShoppingCart> listAll() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        return shoppingCartList;
    }

    /**
     * @Description: 减少一个购物车中的商品
     * @Param: [shoppingCartDTO]
     * @return: void
     */
    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        //先把这条数据找出来
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);

        ShoppingCart cart = shoppingCartList.get(0); //拿到这条数据
        cart.setNumber(cart.getNumber() - 1); //数量-1
        shoppingCartMapper.sub(cart); //更新数量
    }

    /**
     * @Description: 清空购物车
     * @Param: []
     * @return: void
     */
    @Override
    public void clean() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.clean(userId);
    }
}
