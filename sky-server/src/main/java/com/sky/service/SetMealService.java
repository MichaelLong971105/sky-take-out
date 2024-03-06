package com.sky.service;

import com.sky.dto.SetMealDTO;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetMealVO;

import java.util.List;

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

    /**
     * @Description: 批量删除套餐功能
     * @Param: [ids]
     * @return: void
     */
    void deleteSetMeals(List<Long> ids);

    /**
     * @Description: 根据id查询套餐信息
     * @Param: [id]
     * @return: com.sky.vo.SetMealVO
     */
    SetMealVO getSetMealById(Long id);
}
