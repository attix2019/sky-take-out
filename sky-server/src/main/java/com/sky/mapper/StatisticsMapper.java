package com.sky.mapper;

import com.sky.handler.MapResultHandler;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;

@Mapper
public interface StatisticsMapper {

    @MapKey("date1")
    void getTurnoverStatisticsInARange(LocalDate begin, LocalDate end, MapResultHandler mapResultHandler);
}
