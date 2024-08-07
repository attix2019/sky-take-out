package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.Autofill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    @Autofill(operationType = OperationType.INSERT)
    long insertDish(Dish dish);

    Page<DishVO> findDishes(DishPageQueryDTO dishPageQueryDTO);

    void deleteDishes(List<Long> ids);

    Integer countActiveDishByIds(List<Long> ids);

    Dish findDishById(long id);

    @Autofill(operationType = OperationType.UPDATE)
    void updateDish(Dish dish);

    List<Dish> findDishesByCategoryId(int id);

    @Select("select ifnull(count(*),0) from dish where status = #{status} ")
    Integer getDishNumberByStatus(int status);
}
