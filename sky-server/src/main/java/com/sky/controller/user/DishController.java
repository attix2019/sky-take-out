package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userDishController")
@Slf4j
@Api(tags = "用户端菜品相关接口")
@RequestMapping("/user/dish")
public class DishController {

    @Autowired
    DishService dishService;

    @GetMapping("/list")
    @ApiOperation("用户端根据类别获取菜品列表")
    public Result getDishesByCategory(Integer categoryId){
        return Result.success(dishService.listDishesWithFlavorsByCategory(categoryId));
    }
}
