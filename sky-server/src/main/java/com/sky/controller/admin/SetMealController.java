package com.sky.controller.admin;

import com.sky.dto.SetMealDTO;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
