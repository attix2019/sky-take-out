package com.sky.controller.user;

import com.sky.mapper.SetmealMapper;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user/setmeal")
@RestController("userSetmealController")
@Slf4j
@Api("用户端套餐相关接口")
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    @GetMapping("/list")
    @ApiOperation("用户端根据分类id获取套餐列表")
    public Result getSetmealsByCategoryId(Integer categoryId){
        return  Result.success(setmealService.findSetmealsByCategoryId(categoryId));
    }

    @GetMapping("/dish/{id}")
    public Result getSetmealContentById(@PathVariable Integer id){
        return Result.success(setmealService.getSetmealContentById(id));
    }

}
