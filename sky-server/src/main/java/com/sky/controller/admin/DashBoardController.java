package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.DashBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/workspace")
@Slf4j
@Api("工作台相关接口")
public class DashBoardController {

    @Autowired
    DashBoardService dashBoardService;

    @GetMapping("/businessData")
    @ApiOperation("查询今日运营数据")
    public Result getBusinessDataToday(){
        return Result.success(dashBoardService.getBusinessDataToday());
    }
}
