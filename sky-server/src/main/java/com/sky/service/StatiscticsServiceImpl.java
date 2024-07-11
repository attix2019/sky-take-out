package com.sky.service;

import com.sky.handler.MapResultHandler;
import com.sky.mapper.StatisticsMapper;
import com.sky.vo.TurnoverReportVO;
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

    @Override
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {

        List<LocalDate> dateList = new LinkedList<>();
        LocalDate tmp = begin;
        while(!tmp.equals(end)){
            dateList.add(tmp);
            tmp = tmp.plus(1, ChronoUnit.DAYS);
        }
        dateList.add(end);
        List<String> dateStrList = dateList.stream().map(date -> date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .collect(Collectors.toList());
        String dateListStr = String.join(",", dateStrList);

        MapResultHandler mapResultHandler = new MapResultHandler();
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

}
