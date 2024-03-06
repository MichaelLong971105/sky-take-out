package com.sky.controller.admin;

import com.sky.dto.SetMealDTO;
import com.sky.dto.SetMealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import com.sky.vo.SetMealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description: 套餐管理相关功能Controller
 * @author: MichaelLong
 * @create: 2024-03-05 22:16
 **/

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
@Api("套餐管理相关功能")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;

    /**
     * @Description: 新增套餐功能
     * @Param: [setMealDTO]
     * @return: com.sky.result.Result
     */
    @PostMapping
    @ApiOperation("新增套餐功能")
    public Result addSetMeal(@RequestBody SetMealDTO setMealDTO) {
        log.info("新增套餐{}", setMealDTO);
        setMealService.addSetMeal(setMealDTO);
        return Result.success();
    }

    /**
     * @Description: 实现套餐分页查询功能
     * @Param: [setMealPageQueryDTO]
     * @return: com.sky.result.Result<com.sky.result.PageResult>
     */
    @GetMapping("/page")
    @ApiOperation("套餐分页查询功能")
    public Result<PageResult> pageSetMeal(SetMealPageQueryDTO setMealPageQueryDTO) {
        log.info("分页查询套餐:{}", setMealPageQueryDTO);
        PageResult setMealPageResult = setMealService.pageSetMeal(setMealPageQueryDTO);
        return Result.success(setMealPageResult);
    }

    /**
     * @Description: 批量删除套餐功能
     * @Param: [ids]
     * @return: com.sky.result.Result
     */
    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result deleteSetMeals(@RequestParam List<Long> ids) {
        log.info("批量删除套餐:{}", ids);
        setMealService.deleteSetMeals(ids);
        return Result.success();
    }

    /**
     * @Description: 根据id查询套餐信息
     * @Param: [id]
     * @return: com.sky.result.Result<com.sky.vo.SetMealVO>
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐信息")
    public Result<SetMealVO> getSetMealById(@PathVariable Long id) {
        log.info("根据id查询套餐信息:{}", id);
        SetMealVO setMealVO = setMealService.getSetMealById(id);
        return Result.success(setMealVO);
    }

}
