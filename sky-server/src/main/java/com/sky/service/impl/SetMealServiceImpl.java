package com.sky.service.impl;

import com.sky.dto.SetMealDTO;
import com.sky.entity.SetMeal;
import com.sky.entity.SetMealDish;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.service.SetMealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description: 套餐管理相关功能Service实现类
 * @author: MichaelLong
 * @create: 2024-03-05 22:17
 **/

@Service
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    private SetMealMapper setMealMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;

    /**
     * @Description: 新增套餐信息
     * @Param: [setMealDTO]
     * @return: void
     */
    @Override
    public void addSetMeal(SetMealDTO setMealDTO) {

        //把前端传入的SetMealDTO对象复制到SetMeal对象中
        SetMeal setMeal = new SetMeal();
        BeanUtils.copyProperties(setMealDTO, setMeal);

        //把套餐的基本信息插入到数据库中
        setMealMapper.addSetMeal(setMeal);

        //获取套餐中关联的菜品信息
        List<SetMealDish> setMealDishes = setMealDTO.getSetMealDishes();
        //把套餐中关联的菜品信息插入到数据库中
        setMealDishMapper.addSetMealDish(setMealDishes);

    }
}
