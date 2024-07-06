package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {

    @Autowired
    DishService dishService;

    @PostMapping
    @ApiOperation("添加菜品")
    public Result addDish(@RequestBody  DishDTO dishDTO){
        dishService.addDish(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("查询菜品列表")
    public Result<PageResult> findDishes(DishPageQueryDTO dishPageQueryDTO){
        PageResult pageResult = dishService.findDishes(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("删除菜品")
    public Result deleteDishes(String ids){
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
        dishService.deleteDishes(idList);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result findDishById(@PathVariable int id){
        DishVO dish = dishService.findDishById(id);
        return Result.success(dish);
    }

    @PutMapping
    @ApiOperation("编辑菜品信息")
    public Result updateDish(@RequestBody DishDTO dishDTO){
        dishService.updateDish(dishDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("修改菜品起售停售状态")
    public Result updateDishStatus(long id, @PathVariable int status ){
        dishService.updateDishStatus(id, status);
        return Result.success();
    }

    // 在新建套餐选择菜品时用到
    @GetMapping("/list")
    @ApiOperation("根据菜品类别获取在售菜品列表")
    public Result listDishesByCategory(int categoryId){
        List<Dish> dishes =  dishService.listDishesByCategory(categoryId);
        return Result.success(dishes);
    }

}
