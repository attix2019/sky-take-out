package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    DishMapper dishMapper;

    @Autowired
    SetmealMapper setmealMapper;

    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @Override
    @Transactional
    public void addDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insertDish(dish);
        long id = dish.getId();

        List<DishFlavor> flvaors  = dishDTO.getFlavors();
        for(DishFlavor flavor : flvaors){
            flavor.setDishId(id);
        }
        dishFlavorMapper.insertFlavors(flvaors);
    }

    @Override
    public PageResult findDishes(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<Dish> page = dishMapper.findDishes(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void deleteDishes(List<Long> ids) {
        int activeDishesInIds = dishMapper.countActiveDishByIds(ids);
        if( activeDishesInIds != 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }
        int dishesNumberAssociatedwithSetmeal = setmealMapper.countByDishIds(ids);
        if( dishesNumberAssociatedwithSetmeal != 0){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        dishMapper.deleteDishes(ids);
        dishFlavorMapper.deleteFlavorsAssociatedWithDish(ids);
    }

    @Override
    public DishVO findDishById(int id) {
        Dish dish = dishMapper.findDishById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        //需要额外处理categoryName 和 flavors
        List<DishFlavor> flavors = dishFlavorMapper.getFlavorByDishId(id);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    @Override
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.updateDish(dish);
    }
}
