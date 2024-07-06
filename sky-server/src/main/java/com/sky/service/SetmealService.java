package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

public interface SetmealService {

    void addSetmeal(SetmealDTO setmealDTO);

    PageResult findSetmeals(SetmealPageQueryDTO setmealPageQueryDTO);

    void updateSetmealStatus(long id, int status);
}
