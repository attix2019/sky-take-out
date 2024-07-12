package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.StatiscticsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
public class StatisticsController {


    @Autowired
    StatiscticsService statiscticsService;

    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计")
    public Result getTurnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end){
        return Result.success(statiscticsService.getTurnoverStatistics(begin, end));
    }

    @GetMapping("/userStatistics")
    @ApiOperation("用户统计")
    public Result getUserStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end){
        return Result.success(statiscticsService.getUserStatistics(begin, end));
    }

    @GetMapping("/top10")
    @ApiOperation("销量前十统计")
    public Result getTopTenItems(
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end){
        return Result.success(statiscticsService.getTopTenItems(begin, end));
    }

    @GetMapping("/ordersStatistics")
    @ApiOperation("订单数据统计")
    public Result getOrderStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate end){
        return Result.success(statiscticsService.getOrderStatistics(begin, end));
    }

    @GetMapping("/export")
    @ApiOperation("导出过去30天的运营数据")
    public void exportBusinessDataInLast30days(HttpServletResponse response){
        statiscticsService.exportBusinessDataInLast30days(response);
    }
}
