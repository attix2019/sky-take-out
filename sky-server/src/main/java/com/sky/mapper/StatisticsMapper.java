package com.sky.mapper;

import com.sky.handler.MapResultHandler;
import com.sky.handler.UserStatisticResultHandler;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;

@Mapper
public interface StatisticsMapper {

    // 原本返回Map<Date, Double>
    void getTurnoverStatisticsInARange(LocalDate begin, LocalDate end, MapResultHandler mapResultHandler);

    // 原本返回Map<String, Integer>
    void getAccumulatedUserNumber(LocalDate begin, LocalDate end, UserStatisticResultHandler mapResultHandler);


    // 原本返回Map<String, Integer>
    void getNewUserPerDay(LocalDate begin, LocalDate end, UserStatisticResultHandler mapResultHandler);
}
