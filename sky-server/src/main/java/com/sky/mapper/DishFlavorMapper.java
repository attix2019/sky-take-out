package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {


    void insertFlavors(List<DishFlavor> flavors);

    void deleteFlavorsAssociatedWithDish(List<Long> ids);

    List<DishFlavor> getFlavorByDishId(long id);

}
