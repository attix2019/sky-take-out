package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    void addDish(DishDTO dishDTO);

    PageResult findDishes(DishPageQueryDTO dishPageQueryDTO);

    void deleteDishes(List<Long> ids);

    DishVO findDishById(int id);

    void updateDish(DishDTO dishDTO);

    void updateDishStatus(long id, int status);

    List<Dish> listDishesByCategory(int categoryId);
}
