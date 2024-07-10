package com.sky.service;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.OrderDetail;
import com.sky.result.PageResult;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {

    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    void pay(OrdersPaymentDTO ordersPaymentDTO);

    PageResult pageQueryHistoryOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderVO getOrderContentById(long id);

    void repeatOrder(long id);

    void cancelOrder(long id);

    OrderStatisticsVO getOrderStatistics();
}