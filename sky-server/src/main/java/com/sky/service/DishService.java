package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

public interface DishService {

    public void addDish(DishDTO dishDTO);

    public PageResult findDishes(DishPageQueryDTO dishPageQueryDTO);
}
