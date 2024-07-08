package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealMapper setmealMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void addSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        setmealMapper.insertSetmeal(setmeal);
        List<SetmealDish> setmealDishItems = setmealDTO.getSetmealDishes();
        for(SetmealDish setmealDish : setmealDishItems){
            setmealDish.setSetmealId(setmeal.getId());
        }
        setmealDishMapper.insertSetmealDishItem(setmealDishItems);
    }

    @Override
    public PageResult findSetmeals(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.findSetmeals(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void updateSetmealStatus(long id, int status) {
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);
        setmeal.setId(id);
        setmealMapper.updateSetmeal(setmeal);
    }

    @Override
    public SetmealVO getSetmealById(long id) {
        Setmeal setmeal = setmealMapper.getSetmealById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        // 需要额外处理setmeal-dishes
        List<SetmealDish> setmealDishes = setmealDishMapper.findSetmealDishesBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    @Transactional
    public void updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.updateSetmeal(setmeal);
        // 如果菜品发生变化，也要更新菜品
        setmealDishMapper.deleteDishesBySetmealId(Arrays.asList(setmeal.getId()));
        List<SetmealDish> setmealDishItems = setmealDTO.getSetmealDishes();
        for(SetmealDish setmealDish : setmealDishItems){
            setmealDish.setSetmealId(setmeal.getId());
        }
        setmealDishMapper.insertSetmealDishItem(setmealDishItems);
    }

    @Override
    @Transactional
    public void deleteSetmeals(List<Long> idList) {
        // 启用中的套餐不能删除
        int activeSetmealNum = setmealMapper.countActiveSetmealByIds(idList);
        if(activeSetmealNum != 0){
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }
        // 删除套餐表
        setmealMapper.deleteSetmeals(idList);
        // 删除完套餐要把setmeal_dish表里的项一起清理了
        setmealDishMapper.deleteDishesBySetmealId(idList);
    }

    @Override
    public List findSetmealsByCategoryId(Integer categoryId) {
        return setmealMapper.findSetmealsByCategoryID(categoryId);
    }
}
