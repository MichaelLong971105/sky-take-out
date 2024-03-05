package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description: 菜品相关Service接口实现类
 * @author: MichaelLong
 * @create: 2024-03-01 23:42
 **/
@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @Description: 新增菜品和对应的口味数据
     * @Param: [dishDTO]
     * @return: void
     */
    @Override
    @Transactional
    public void addDishWithFlavor(DishDTO dishDTO) {

        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //向菜品表中插入1条数据
        dishMapper.addDish(dish);

        //获取新增菜品的主键id值
        Long dishId = dish.getId();

        //向口味表插入n条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });

            dishFlavorMapper.insertBatch(flavors);
        }

    }

    /**
     * @Description: 分页查询菜品信息
     * @Param: [dishPageQueryDTO]
     * @return: com.sky.result.PageResult
     */
    @Override
    public PageResult pageDish(DishPageQueryDTO dishPageQueryDTO) {
        //使用PageHelper进行分页查询
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.pageDish(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * @Description: 菜品批量删除
     * @Param: [ids]
     * @return: void
     */
    @Override
    @Transactional
    public void deleteDishes(List<Long> ids) {
        //判断当前菜品是否能删除 -> 如果菜品状态是起售中则不能删除
        for (Long id : ids) {
            Dish dish = dishMapper.getDishById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                //当前菜品起售中，不能删除
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //判断当前菜品是否能删除 -> 如果菜品在某个套餐中关联了则不能删除
        List<Long> setSetMealIds = setMealDishMapper.getSetMealIdsByDishIds(ids);
        if (setSetMealIds != null && setSetMealIds.size() > 0) {
            //当前菜品被套餐关联了
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //批量删除菜品表中的菜品数据
        dishMapper.deleteDishByIds(ids);

        //批量删除口味表中该菜品关联的数据
        dishFlavorMapper.deleteFlavorByIds(ids);

    }

    /**
     * @Description: 根据id查询菜品信息
     * @Param: [id]
     * @return: com.sky.vo.DishVO
     */
    @Override
    public DishVO getDishById(Long id) {

        //获取菜品基本信息
        Dish dish = dishMapper.getDishById(id);

        //获取菜品口味信息
        List<DishFlavor> dishFlavors = dishFlavorMapper.getFlavorsByDishId(id);

        //根据菜品的分类的id获取相应的分类名称
        //可以不写，因为前面分页查询的时候已经有查询到了分类名称，前端会自动复用
//        String categoryName = categoryMapper.getCategoryByDishId(dish.getCategoryId());

        //把上述信息封装到DishVO对象中
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
//        dishVO.setCategoryName(categoryName);
        dishVO.setFlavors(dishFlavors);

        //返回封装好的DishVO对象
        return dishVO;
    }

    /**
     * @Description: 修改菜品信息
     * @Param: [dishDTO]
     * @return: void
     */
    @Override
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();

        //把前端传递的DishDTO对象的信息复制到Dish对象中
        BeanUtils.copyProperties(dishDTO, dish);

        //更新修改日期
        dish.setUpdateTime(LocalDateTime.now());
        //更新修改人id
        dish.setUpdateUser(BaseContext.getCurrentId());
        //更新菜品信息
        dishMapper.updateDish(dish);
        //先从数据库中删除菜品的所有口味信息
        dishFlavorMapper.deleteFlavorByIds(Collections.singletonList(dish.getId()));

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            //把菜品id和口味关联起来
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
            });
            //再把菜品的所有口味重新插入数据库
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * @Description: 修改菜品状态(起售, 停售)
     * @Param: [id]
     * @return: void
     */
    @Override
    public void updateDishStatus(Integer status, Long id) {
        dishMapper.updateDishStatus(status, id);
    }
}
