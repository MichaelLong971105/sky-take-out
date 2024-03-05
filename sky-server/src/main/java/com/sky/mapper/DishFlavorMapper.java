package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description: 菜品口味相关Mapper
 * @author: MichaelLong
 * @create: 2024-03-02 00:04
 **/
@Mapper
public interface DishFlavorMapper {

    /**
     * @Description: 批量插入菜品口味
     * @Param: [flavors]
     * @return: void
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * @Description: 根据菜品id批量删除对应的口味信息
     * @Param: [dishIds]
     * @return: void
     */
    void deleteFlavorByIds(List<Long> dishIds);

    /**
     * @Description: 根据菜品id获取菜品相关口味信息
     * @Param: [id]
     * @return: java.util.List<com.sky.entity.DishFlavor>
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getFlavorsByDishId(Long dishId);

}
