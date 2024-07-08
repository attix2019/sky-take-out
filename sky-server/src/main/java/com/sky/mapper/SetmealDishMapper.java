package com.sky.mapper;

import com.sky.entity.SetmealDish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    void insertSetmealDishItem(List<SetmealDish> setmealDishItems);

    @Select("select s.*, d.image from setmeal_dish as s left join dish as d on s.dish_id=d.id where setmeal_id=#{id}")
    List<SetmealDish> findSetmealDishesBySetmealId(long id);

    void deleteDishesBySetmealId(List<Long> idList);
}
