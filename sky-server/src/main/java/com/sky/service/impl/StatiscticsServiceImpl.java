package com.sky.service.impl;

import com.sky.handler.MapResultHandler;
import com.sky.mapper.StatisticsMapper;
import com.sky.service.StatiscticsService;
import com.sky.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatiscticsServiceImpl implements StatiscticsService {

    @Autowired
    StatisticsMapper statisticsMapper;

    private List<String> createDateStringList(LocalDate begin, LocalDate end){
        List<LocalDate> dateList = new LinkedList<>();
        LocalDate tmp = begin;
        while(!tmp.equals(end)){
            dateList.add(tmp);
            tmp = tmp.plus(1, ChronoUnit.DAYS);
        }
        dateList.add(end);
        List<String> dateStrList = dateList.stream().map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .collect(Collectors.toList());
        return dateStrList;
    }

    private  <B> List<String> generateFittingList(Map<String,B> handledMap, List<String> dates, Object defaultValue){
        List<String> targetList = new LinkedList<>();
        for(String datestr : dates){
            B turnover  = handledMap.getOrDefault( datestr , (B)defaultValue);
            targetList.add(String.valueOf(turnover));
        }
        return targetList;
    }

    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        List<String> dateStrList = createDateStringList(begin, end);
        String dateListStr = String.join(",", dateStrList);

        MapResultHandler mapResultHandler = new MapResultHandler();
        statisticsMapper.getTurnoverStatisticsInARange(begin, end, mapResultHandler);
        Map<String, Double> handledResult = mapResultHandler.getMappedResults();
        List<String> turnoverStrList = generateFittingList(handledResult, dateStrList, 0.0);
        String turnoverList = String.join(",", turnoverStrList);

        return TurnoverReportVO.builder()
                .dateList(dateListStr)
                .turnoverList(turnoverList)
                .build();
    }


    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        List<String> dateStrList = createDateStringList(begin, end);
        String dateListStr = String.join(",", dateStrList);

        MapResultHandler mapResultHandler = new MapResultHandler();
        statisticsMapper.getNewUserPerDay( begin, end, mapResultHandler );
        Map<String, Integer> userPerdayStatistic = mapResultHandler.getMappedResults();
        List<String> newUserStrList = generateFittingList(userPerdayStatistic, dateStrList, 0);
        String newUserList = String.join(",", newUserStrList);


        mapResultHandler = new MapResultHandler();
        statisticsMapper.getAccumulatedUserNumber(begin, end, mapResultHandler);
        Map<String, Integer> userTotal = mapResultHandler.getMappedResults();
        List<String> userTotalList = generateFittingList(userTotal, dateStrList, 0);
        String totalUserList = String.join(",", userTotalList);

        return UserReportVO.builder().
                dateList(dateListStr).
                newUserList(newUserList)
                .totalUserList(totalUserList)
                .build();
    }

    @Override
    public OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end) {
        List<String> dateStrList = createDateStringList(begin, end);
        String dateListStr = String.join(",", dateStrList);

        MapResultHandler mapResultHandler = new MapResultHandler();
        List<OrderReportItem> list = statisticsMapper.getOrderStatistic(begin, end, mapResultHandler);

        List<String> orderCounts = new LinkedList<>();
        List<String> validOrderCounts = new LinkedList<>();
        Map<String, OrderReportItem> items  = new HashMap<>();
        for(OrderReportItem orderReportItem : list){
            String date = orderReportItem.getDate();
            items.put(date, orderReportItem);
        }

        for(String date : dateStrList){
            OrderReportItem orderReportItem = items.getOrDefault(date, new OrderReportItem(date,0,0) );
            orderCounts.add(orderReportItem.getOrderCount().toString());
            validOrderCounts.add(orderReportItem.getValidOrderCount().toString());
        }

        int orderCount = statisticsMapper.countOrdersInTotal(begin, end);
        int validOrderCount = statisticsMapper.countValidOrders(begin, end);
        double ratio =  (orderCount == 0 ? 0 :(double)validOrderCount/orderCount) ;
        return OrderReportVO.builder()
                .dateList(dateListStr)
                .orderCountList(String.join(",", orderCounts))
                .validOrderCountList(String.join(",", validOrderCounts))
                .totalOrderCount(orderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(ratio)
                .build();
    }

    @Override
    public SalesTop10ReportVO getTopTenItems(LocalDate begin, LocalDate end){
        List<SalesTop10Item> top10Items = statisticsMapper.getTopTenItems(begin, end);
        List<String> names = new LinkedList<>();
        List<String> number = new LinkedList<>();
        for(SalesTop10Item top10Item : top10Items){
            names.add(top10Item.getName());
            number.add(top10Item.getNumber().toString());
        }
        return SalesTop10ReportVO.builder()
                .nameList(String.join("," ,names))
                .numberList( String.join(",", number))
                .build();
    }

}
