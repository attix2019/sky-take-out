package com.sky.service;

import com.sky.handler.MapResultHandler;
import com.sky.mapper.StatisticsMapper;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatiscticsServiceImpl implements  StatiscticsService {

    @Autowired
    StatisticsMapper statisticsMapper;

    @Autowired
    MapResultHandler mapResultHandler;

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


    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        List<String> dateStrList = createDateStringList(begin, end);
        String dateListStr = String.join(",", dateStrList);

        statisticsMapper.getTurnoverStatisticsInARange(begin, end, mapResultHandler);
        Map<Date, Double> handledResult = mapResultHandler.getMappedResults();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<String> turnoverStrList = new LinkedList<>();
        for(String datestr : dateStrList){
            Double turnover = null;
            try {
                turnover = handledResult.getOrDefault( formatter.parse(datestr) , 0.0);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            turnoverStrList.add(String.valueOf(turnover));
        }
        String turnoverList = String.join(",", turnoverStrList);
        return TurnoverReportVO.builder().dateList(dateListStr).turnoverList(turnoverList).build();
    }

    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        List<String> dateStrList = createDateStringList(begin, end);
        String dateListStr = String.join(",", dateStrList);


        statisticsMapper.getNewUserPerDay( begin, end, mapResultHandler );
        Map<String, Integer> userPerdayStatistic = mapResultHandler.getMappedResults();

        List<String> newUserStrList = new LinkedList<>();
        for(String datestr : dateStrList){
            Integer newUserCount = userPerdayStatistic.getOrDefault( datestr , 0);
            newUserStrList.add(String.valueOf(newUserCount));
        }
        String newUserList = String.join(",", newUserStrList);


        statisticsMapper.getAccumulatedUserNumber(begin, end, mapResultHandler);
        Map<String, Integer> userTotal = mapResultHandler.getMappedResults();
        List<String> userTotalList = new LinkedList<>();
        for(String datestr : dateStrList){
            Integer newUserCount = userTotal.getOrDefault( datestr , 0);
            userTotalList.add(String.valueOf(newUserCount));
        }
        String totalUserList = String.join(",", userTotalList);

        return UserReportVO.builder().
                dateList(dateListStr).
                newUserList(newUserList)
                .totalUserList(totalUserList)
                .build();
    }

}
