package com.sky.controller.user;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/order")
@Slf4j
@Api("订单相关接口")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/submit")
    @ApiOperation("下单")
    public Result submitOrder(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        return Result.success(orderService.submit(ordersSubmitDTO));
    }

    // 暂时模拟支付成功
    @PutMapping("payment")
    @ApiOperation("订单支付接口")
    public Result pay(@RequestBody OrdersPaymentDTO ordersPaymentDTO){
        orderService.pay(ordersPaymentDTO);
        return Result.success();
    }

    @GetMapping("/historyOrders")
    @ApiOperation("获取历史订单")
    public Result getHistoryOrders(OrdersPageQueryDTO ordersPageQueryDTO){
        return Result.success(orderService.pageQueryHistoryOrders(ordersPageQueryDTO));
    }
}
