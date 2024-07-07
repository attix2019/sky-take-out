package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/admin/setmeal")
@Api("套餐相关接口")
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

    @GetMapping("/{id}")
    @ApiOperation("根据id获取套餐详情")
    public Result getSetmealById(@PathVariable long id){
        SetmealVO setmealVO = setmealService.getSetmealById(id);
        return Result.success(setmealVO);
    }

    @PutMapping
    @ApiOperation("编辑套餐")
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }


    @PostMapping("/status/{status}")
    @ApiOperation("修改套餐起售停售状态")
    public Result updateSetmealStatus(long id, @PathVariable int status){
        setmealService.updateSetmealStatus(id, status);
        return Result.success();
    }

    // 删除
    @DeleteMapping
    @ApiOperation("删除套餐")
    public Result deleteSetmeals(String ids){
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
        setmealService.deleteSetmeals(idList);
        return Result.success();
    }
}
