package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description: 购物车相关Mapper
 * @author: MichaelLong
 * @create: 2024-03-18 23:02
 **/

@Mapper
public interface ShoppingCartMapper {

    /**
     * @Description: 动态查询购物车中商品信息
     * @Param: [shoppingCart]
     * @return: java.util.List<com.sky.entity.ShoppingCart>
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * @Description: 根据id更新购物车中的数据
     * @Param: [shoppingCart]
     * @return: void
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * @Description: 插入购物车数据
     * @Param: [shoppingCart]
     * @return: void
     */
    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time) " +
            "VALUES (#{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{createTime})")
    void insert(ShoppingCart shoppingCart);

    /**
     * @Description: 减少一个购物车中的商品
     * @Param: [shoppingCart]
     * @return: void
     */
    void sub(ShoppingCart shoppingCart);

    /**
     * @Description: 清空购物车
     * @Param: [currentId]
     * @return: void
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void clean(Long userId);

    /**
     * @Description: 再来一单，往购物车中添加多条数据
     * @Param: [shoppingCartList]
     * @return: void
     */
    void insertOldOrder(List<ShoppingCart> shoppingCartList);

    /**
     * @Description: 当把某件商品数量减到0时删除该记录
     * @Param: [cart]
     * @return: void
     */
    void subToZero(ShoppingCart cart);
}
