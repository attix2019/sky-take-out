package com.sky.mapper;

import com.sky.handler.MapResultHandler;
import com.sky.vo.OrderReportItem;
import com.sky.vo.SalesTop10Item;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsMapper {

    // 原本返回Map<Date, Double>
    void getTurnoverStatisticsInARange(LocalDate begin, LocalDate end, MapResultHandler mapResultHandler);

    // 原本返回Map<String, Integer>
    void getAccumulatedUserNumber(LocalDate begin, LocalDate end, MapResultHandler mapResultHandler);

    // 原本返回Map<String, Integer>
    void getNewUserPerDay(LocalDate begin, LocalDate end, MapResultHandler mapResultHandler);

    List<SalesTop10Item> getTopTenItems(LocalDate begin, LocalDate end);

    List<OrderReportItem> getOrderStatistic(LocalDate begin, LocalDate end, MapResultHandler mapResultHandler);

    Integer countOrdersInTotal(LocalDate begin, LocalDate end);

    Integer countValidOrders(LocalDate begin, LocalDate end);
}
