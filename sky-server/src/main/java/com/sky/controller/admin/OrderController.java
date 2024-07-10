package com.sky.controller.admin;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
