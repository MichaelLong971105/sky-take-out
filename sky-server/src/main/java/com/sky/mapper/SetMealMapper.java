package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.entity.SetMeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetMealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
