package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.result.Result;
import com.sky.service.ShoppingcartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/shoppingCart")
public class ShoppingcartController {


    @Autowired
    ShoppingcartService shoppingcartService;

    @PostMapping("/add")
    @ApiOperation("添加商品到购物车")
    public Result addGoodToShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shoppingcartService.insertGoodToShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查看购物车列表")
    public Result listShoppingCartItems(){
        return  Result.success(shoppingcartService.listShoppingCartItems());
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result clearShoppingCart(){
        shoppingcartService.clearShoppingCart();
        return Result.success();
    }
}
