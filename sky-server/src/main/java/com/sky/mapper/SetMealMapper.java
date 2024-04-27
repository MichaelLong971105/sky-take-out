package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.entity.SetMeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetMealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface SetMealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * @Description: 新增套餐信息
     * @Param: [setMeal]
     * @return: void
     */
    @AutoFill(value = OperationType.INSERT)
    void addSetMeal(SetMeal setMeal);

    /**
     * @Description: 套餐分页查询
     * @Param: [setMealPageQueryDTO]
     * @return: com.github.pagehelper.Page<com.sky.entity.SetMeal>
     */
    Page<SetMealVO> pageSetMeal(SetMealPageQueryDTO setMealPageQueryDTO);

    /**
     * @Description: 根据id查询套餐信息
     * @Param: [id]
     * @return: com.sky.entity.SetMeal
     */
    SetMeal getSetMealById(Long id);

    /**
     * @Description: 根据id删除套餐信息
     * @Param: [ids]
     * @return: void
     */
    void deleteSetMeals(List<Long> ids);

    /**
     * @Description: 修改套餐相关信息
     * @Param: [setMeal]
     * @return: void
     */
    void updateSetMeal(SetMeal setMeal);

    /** 
     * @Description: 修改套餐起售、停售状态功能
     * @Param: [setMealId, status]
     * @return: void
     */
    @Update("update setmeal set status = #{status} where id = #{setMealId}")
    void updateSetMealStatus(Long setMealId, Integer status);

    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<SetMeal> list(SetMeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    Integer countByMap(Map map);
}
