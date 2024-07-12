package com.sky.service.impl;

import com.sky.mapper.OrderMapper;
import com.sky.mapper.StatisticsMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.DashBoardService;
import com.sky.vo.BusinessDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StatisticsMapper statisticsMapper;

    @Override
    public BusinessDataVO getBusinessDataToday() {
        Double turnOverToday = orderMapper.getTurnOverToday();
        int validOrdersCount = statisticsMapper.countValidOrders(LocalDate.now(),LocalDate.now());
        int ordersCountInTotal = statisticsMapper.countOrdersInTotal(LocalDate.now(),LocalDate.now());
        int newUserCountToday = userMapper.countNewUserToday();

        return BusinessDataVO.builder()
                .turnover(turnOverToday)
                .validOrderCount(validOrdersCount)
                .orderCompletionRate(ordersCountInTotal == 0? 0 : (double)validOrdersCount/ordersCountInTotal)
                .newUsers(newUserCountToday)
                .unitPrice(validOrdersCount == 0 ? 0 : turnOverToday/validOrdersCount)
                .build();
    }
}
