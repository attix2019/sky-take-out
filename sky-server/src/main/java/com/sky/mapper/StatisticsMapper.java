package com.sky.mapper;

import com.sky.handler.MapResultHandler;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.Map;

@Mapper
public interface StatisticsMapper {

    // 原本返回Map<Date, Double>
    void getTurnoverStatisticsInARange(LocalDate begin, LocalDate end, MapResultHandler mapResultHandler);

    // 原本返回Map<String, Integer>
    void getAccumulatedUserNumber(LocalDate begin, LocalDate end, MapResultHandler mapResultHandler);

    // 原本返回Map<String, Integer>
    void getNewUserPerDay(LocalDate begin, LocalDate end, MapResultHandler mapResultHandler);
}
