package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    DishService dishService;

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

}
