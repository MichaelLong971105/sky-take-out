package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import org.springframework.stereotype.Component;

/**
 * @program: sky-take-out-backend
 * @description: 菜品相关Service接口
 * @author: MichaelLong
 * @create: 2024-03-01 23:42
 **/
public interface DishService {

    /**
     * @Description: 新增菜品和对应的口味数据
     * @Param: [dishDTO]
     * @return: void
     */
    public void addDishWithFlavor(DishDTO dishDTO);

    /**
     * @Description: 分页查询菜品信息
     * @Param: [dishPageQueryDTO]
     * @return: com.sky.result.PageResult
     */
    PageResult pageDish(DishPageQueryDTO dishPageQueryDTO);
}
