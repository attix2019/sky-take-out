package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderSubmitVO;

public interface OrderService {

    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    void pay(OrdersPaymentDTO ordersPaymentDTO);

    PageResult pageQueryHistoryOrders(OrdersPageQueryDTO ordersPageQueryDTO);
}
