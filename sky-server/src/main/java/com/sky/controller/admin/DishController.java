package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description: 菜品相关控制器
 * @author: MichaelLong
 * @create: 2024-03-01 23:40
 **/

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关控制器")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * @Description: 新增菜品
     * @Param: [dishDTO]
     * @return: com.sky.result.Result
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result addDish(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品:{}", dishDTO);
        dishService.addDishWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * @Description: 菜品分页查询
     * @Param: [dishPageQueryDTO]
     * @return: com.sky.result.Result<com.sky.result.PageResult>
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询功能")
    public Result<PageResult> pageDish(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品:{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageDish(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * @Description: 菜品批量删除功能
     * @Param: [ids]
     * @return: com.sky.result.Result
     */
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result deleteDishes(@RequestParam List<Long> ids) {
        log.info("批量删除菜品:{}", ids);
        dishService.deleteDishes(ids);
        return Result.success();
    }

    /**
     * @Description: 根据id查询菜品信息
     * @Param: [id]
     * @return: com.sky.result.Result<com.sky.vo.DishVO>
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品信息")
    public Result<DishVO> getDishById(@PathVariable Long id) {
        log.info("根据id查询菜品信息:{}", id);
        DishVO dish = dishService.getDishById(id);
        return Result.success(dish);
    }

    /**
     * @Description: 修改菜品信息
     * @Param: [dishDTO]
     * @return: com.sky.result.Result
     */
    @PutMapping
    @ApiOperation("修改菜品信息")
    public Result updateDish(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品信息:{}", dishDTO);
        dishService.updateDish(dishDTO);
        return Result.success();
    }

    /**
     * @Description: 修改菜品状态(起售,停售)
     * @Param: [id]
     * @return: com.sky.result.Result
     */
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售、停售")
    public Result updateDishStatus(@PathVariable Integer status, Long id) {
        log.info("修改菜品{}起售停售信息:{}", id, status);
        dishService.updateDishStatus(status, id);
        return Result.success();
    }

}
