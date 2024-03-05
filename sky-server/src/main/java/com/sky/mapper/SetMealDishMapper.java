package com.sky.mapper;

import com.sky.entity.SetMealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description: 操作套餐表
 * @author: MichaelLong
 * @create: 2024-03-03 20:34
 **/

@Mapper
public interface SetMealDishMapper {

    /**
     * @Description: 根据菜品id查询对应的套餐id
     * @Param: [dishIds]
     * @return: java.util.List<java.lang.Long>
     */
    List<Long> getSetMealIdsByDishIds(List<Long> dishIds);

    void addSetMealDish(List<SetMealDish> setMealDishes);
}
