package com.sky.service;

import com.sky.dto.SetMealDTO;

/**
 * @program: sky-take-out-backend
 * @description: 套餐管理相关功能Service
 * @author: MichaelLong
 * @create: 2024-03-05 22:16
 **/
public interface SetMealService {
    /**
     * @Description: 新增套餐信息
     * @Param: [SetMealDTO]
     * @return: void
     */
    void addSetMeal(SetMealDTO setMealDTO);
}
