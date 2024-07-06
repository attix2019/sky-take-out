package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    void insertSetmealDishItem(List<SetmealDish> setmealDishItems);

    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> findSetmealDishesBySetmealId(long id);

    @Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteDishesBySetmealId(long id);
}
