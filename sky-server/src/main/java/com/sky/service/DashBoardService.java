package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.OrderOverViewVO;

public interface DashBoardService {

    BusinessDataVO getBusinessDataToday();

    OrderOverViewVO getOrderOverview();
}
