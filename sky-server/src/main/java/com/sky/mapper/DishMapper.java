package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * @Description: 插入菜品数据
     * @Param: [dish]
     * @return: void
     */
    @AutoFill(value = OperationType.INSERT)
    void addDish(Dish dish);

    /**
     * @Description: 分页查询菜品信息
     * @Param: [dishPageQueryDTO]
     * @return: com.github.pagehelper.Page<com.sky.entity.Dish>
     */
    Page<DishVO> pageDish(DishPageQueryDTO dishPageQueryDTO);
}
