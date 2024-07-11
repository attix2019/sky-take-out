package com.sky.service;

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

public interface StatiscticsService {

    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);
}
