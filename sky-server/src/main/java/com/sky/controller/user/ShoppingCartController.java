package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: sky-take-out-backend
 * @description: 购物车功能相关Controller
 * @author: MichaelLong
 * @create: 2024-03-18 22:54
 **/

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api("用户端购物车相关接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * @Description: 添加购物车功能
     * @Param: [shoppingCartDTO]
     * @return: com.sky.result.Result
     */
    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车，商品信息为:", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * @Description: 查看购物车
     * @Param: []
     * @return: com.sky.result.Result<com.sky.entity.ShoppingCart>
     */
    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List<ShoppingCart>> list() {
        log.info("查看购物车");
        List<ShoppingCart> shoppingCartList = shoppingCartService.listAll();
        return Result.success(shoppingCartList);
    }

    /** 
     * @Description: 减少一个购物车中的商品
     * @Param: [shoppingCartDTO]
     * @return: com.sky.result.Result
     */
    @PostMapping("/sub")
    @ApiOperation("减少一个商品的接口")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("减少一个商品:{}", shoppingCartDTO);
        shoppingCartService.sub(shoppingCartDTO);
        return Result.success();
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result clean() {
        log.info("清空购物车");
        shoppingCartService.clean();
        return Result.success();
    }

}
