package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    /**
     * @Description: 根据id查询菜品信息
     * @Param: [id]
     * @return: com.sky.entity.Dish
     */
    @Select("select * from dish where id = #{id}")
    Dish getDishById(Long id);

    /**
     * @Description: 根据id批量删除菜品信息
     * @Param: [ids]
     * @return: void
     */
    void deleteDishByIds(List<Long> ids);

    /**
     * @Description: 修改菜品信息
     * @Param: [dishDTO]
     * @return: void
     */
    void updateDish(Dish dish);

    /**
     * @Description: 修改菜品状态(起售,停售)
     * @Param: [id]
     * @return: void
     */
    @Update("update dish set status = #{status} where id = #{id}")
    void updateDishStatus(Integer status, Long id);

    /**
     * @Description: 根据菜品分类id获取关联的菜品信息
     * @Param: [categoryId]
     * @return: java.util.List<com.sky.entity.Dish>
     */
    List<Dish> getDishByCategoryId(Long categoryId);

    List<Dish> list(Dish dish);
}
