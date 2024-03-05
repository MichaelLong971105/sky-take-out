package com.sky.service;

import com.sky.dto.SetMealDTO;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.result.PageResult;

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

    /**
     * @Description: 实现套餐分页查询功能
     * @Param: [setMealPageQueryDTO]
     * @return: com.sky.result.PageResult
     */
    PageResult pageSetMeal(SetMealPageQueryDTO setMealPageQueryDTO);
}
