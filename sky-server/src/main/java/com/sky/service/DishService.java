package com.sky.service;

import com.sky.dto.DishDTO;
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

}
