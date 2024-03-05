package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.stereotype.Component;

import java.util.List;

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

    /**
     * @Description: 菜品批量删除
     * @Param: [ids]
     * @return: void
     */
    void deleteDishes(List<Long> ids);

    /**
     * @Description: 根据id查询菜品信息
     * @Param: [id]
     * @return: com.sky.vo.DishVO
     */
    DishVO getDishById(Long id);

    /**
     * @Description: 修改菜品信息
     * @Param: [dishDTO]
     * @return: void
     */
    void updateDish(DishDTO dishDTO);

    /**
     * @Description: 修改菜品状态(起售,停售)
     * @Param: [id]
     * @return: void
     */
    void updateDishStatus(Integer status, Long id);
}
