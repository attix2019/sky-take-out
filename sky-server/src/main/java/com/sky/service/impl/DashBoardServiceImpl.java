package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.*;
import com.sky.service.DashBoardService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StatisticsMapper statisticsMapper;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    SetmealMapper setmealMapper;

    @Override
    public BusinessDataVO getBusinessDataInRange(LocalDate begin, LocalDate end) {
        Double turnOverToday = orderMapper.getTurnOverToday(begin, end);
        int validOrdersCount = statisticsMapper.countValidOrders(begin, end);
        int ordersCountInTotal = statisticsMapper.countOrdersInTotal(begin, end);
        int newUserCountToday = userMapper.countNewUserInRange(begin, end);

        return BusinessDataVO.builder()
                .turnover(turnOverToday)
                .validOrderCount(validOrdersCount)
                .orderCompletionRate(ordersCountInTotal == 0? 0 : (double)validOrdersCount/ordersCountInTotal)
                .newUsers(newUserCountToday)
                .unitPrice(validOrdersCount == 0 ? 0 : turnOverToday/validOrdersCount)
                .build();
    }

    @Override
    public OrderOverViewVO getOrderOverview() {
        LocalDate today = LocalDate.now();
        int ordersToBeConfirmedNumber = orderMapper.getOrderCountByStatusAndDate(today,
                Arrays.asList(Orders.TO_BE_CONFIRMED));
        int ordersToDeliverNumber = orderMapper.getOrderCountByStatusAndDate(today,
                Arrays.asList(Orders.CONFIRMED));
        int finishedOrdersNumber = orderMapper.getOrderCountByStatusAndDate(today,
                Arrays.asList(Orders.COMPLETED));
        int canceledOrdersNumber = orderMapper.getOrderCountByStatusAndDate(today,
                Arrays.asList(Orders.CANCELLED));
        int orderNumberInTotal =  orderMapper.getOrderCountByStatusAndDate(today,
                Arrays.asList(Orders.PENDING_PAYMENT,
                        Orders.TO_BE_CONFIRMED,
                        Orders.CONFIRMED,
                        Orders.DELIVERY_IN_PROGRESS,
                        Orders.COMPLETED,
                        Orders.CANCELLED));
        return OrderOverViewVO.builder()
                .allOrders(orderNumberInTotal)
                .cancelledOrders(canceledOrdersNumber)
                .completedOrders(finishedOrdersNumber)
                .waitingOrders(ordersToBeConfirmedNumber)
                .deliveredOrders(ordersToDeliverNumber)
                .build();
    }

    @Override
    public DishOverViewVO getDishOverview() {
        int onSale = dishMapper.getDishNumberByStatus(1);
        int outOfSale = dishMapper.getDishNumberByStatus(0);
        return DishOverViewVO.builder().sold(onSale).discontinued(outOfSale).build();
    }

    @Override
    public SetmealOverViewVO getSetmealOverview() {
        int onSale = setmealMapper.getSetmealNumberByStatus(1);
        int outOfSale = setmealMapper.getSetmealNumberByStatus(0);
        return SetmealOverViewVO.builder().sold(onSale).discontinued(outOfSale).build();
    }
}
