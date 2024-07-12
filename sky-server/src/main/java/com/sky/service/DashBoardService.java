package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

import java.time.LocalDate;

public interface DashBoardService {

    BusinessDataVO getBusinessDataInRange(LocalDate begin, LocalDate end);

    OrderOverViewVO getOrderOverview();

    DishOverViewVO getDishOverview();

    SetmealOverViewVO getSetmealOverview();
}
