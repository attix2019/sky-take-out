package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/confirm")
    @ApiOperation("接受订单")
    public Result confirmOrder(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        orderService.confirmOrder(ordersConfirmDTO.getId());
        return Result.success();
    }


    @PutMapping("/rejection")
    @ApiOperation("拒绝订单")
    public Result rejectOrder(@RequestBody OrdersRejectionDTO ordersRejectionDTO){
        orderService.rejectOrder(ordersRejectionDTO);
        return Result.success();
    }

    // 用户支付以后可以拒绝订单，确认订单以后可以取消订单
    @PutMapping("/cancel")
    @ApiOperation("取消订单")
    public Result rejectOrder(@RequestBody OrdersCancelDTO ordersCancelDTO){
        orderService.cancelOrer(ordersCancelDTO);
        return Result.success();
    }

}
