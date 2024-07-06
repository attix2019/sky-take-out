package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    @PostMapping
    @ApiOperation("添加套餐")
    public Result addSetmeal(@RequestBody SetmealDTO setmealDTO){
        setmealService.addSetmeal(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("查询套餐列表")
    public Result<PageResult> findSetmeals(SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult pageResult =  setmealService.findSetmeals(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

     // 修改套餐

    @GetMapping("/{id}")
    @ApiOperation("根据id获取套餐详情")
    public Result getSetmealById(@PathVariable long id){
        SetmealVO setmealVO = setmealService.getSetmealById(id);
        return Result.success(setmealVO);
    }


    @PostMapping("/status/{status}")
    @ApiOperation("修改套餐起售停售状态")
    public Result updateSetmealStatus(long id, @PathVariable int status){
        setmealService.updateSetmealStatus(id, status);
        return Result.success();
    }

    // 删除
}
