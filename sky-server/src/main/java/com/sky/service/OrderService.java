package com.sky.service;

import com.sky.dto.*;
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

    void confirmOrder(long id);

    void rejectOrder(OrdersRejectionDTO ordersRejectionDTO);

    void cancelOrer(OrdersCancelDTO ordersCancelDTO);
}
