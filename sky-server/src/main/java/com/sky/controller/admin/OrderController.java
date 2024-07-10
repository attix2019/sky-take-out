package com.sky.controller.admin;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminOrderController")
@Slf4j
@RequestMapping("/admin/order")
@Api("用户端订单相关接口")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/conditionSearch")
    @ApiOperation("管理端查询订单列表")
    public Result pageQueryOrders(OrdersPageQueryDTO ordersPageQueryDTO){
        return Result.success(orderService.pageQueryHistoryOrders(ordersPageQueryDTO));
    }


    @GetMapping("/statistics")
    @ApiOperation("各个状态的订单数量统计")
    public Result getOrderStatistics(){
        return  Result.success(orderService.getOrderStatistics());
    }

    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result getOrderContentById(@PathVariable long id){
        return Result.success(orderService.getOrderContentById(id));
    }

}