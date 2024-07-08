package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCartItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper {

    void insertItem(ShoppingCartItem shoppingCartItem);

    ShoppingCartItem getExistingItem(ShoppingCartItem shoppingCartItem);

    void updateItem(ShoppingCartItem shoppingCartItem);
}
