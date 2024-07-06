package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

public interface SetmealService {

    void addSetmeal(SetmealDTO setmealDTO);

    PageResult findSetmeals(SetmealPageQueryDTO setmealPageQueryDTO);

    void updateSetmealStatus(long id, int status);

    SetmealVO getSetmealById(long id);

    void updateSetmeal(SetmealDTO setmealDTO);
}
