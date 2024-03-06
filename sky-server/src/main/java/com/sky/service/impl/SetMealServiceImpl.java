package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetMealDTO;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.entity.SetMeal;
import com.sky.entity.SetMealDish;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetMealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetMealService;
import com.sky.vo.SetMealVO;
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
        Long setMealId = setMeal.getId();

        //获取套餐中关联的菜品信息
        List<SetMealDish> setMealDishes = setMealDTO.getSetmealDishes();
        //把菜品跟套餐id绑定
        for (SetMealDish setMealDish : setMealDishes) {
            setMealDish.setSetMealId(setMealId);
        }
        //把套餐中关联的菜品信息插入到数据库中
        setMealDishMapper.addSetMealDish(setMealDishes);

    }

    /**
     * @Description: 实现套餐分页查询功能
     * @Param: [setMealPageQueryDTO]
     * @return: com.sky.result.PageResult
     */
    @Override
    public PageResult pageSetMeal(SetMealPageQueryDTO setMealPageQueryDTO) {
        //使用PageHelper进行分页查询
        PageHelper.startPage(setMealPageQueryDTO.getPage(), setMealPageQueryDTO.getPageSize());

        Page<SetMealVO> page = setMealMapper.pageSetMeal(setMealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());

    }
}
