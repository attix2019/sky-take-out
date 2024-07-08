package com.sky.mapper;

import com.sky.entity.ShoppingCartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    void insertItem(ShoppingCartItem shoppingCartItem);

    ShoppingCartItem getExistingItem(ShoppingCartItem shoppingCartItem);

    void updateItem(ShoppingCartItem shoppingCartItem);

    @Select("select * from shopping_cart where user_id = #{userId}")
    List<ShoppingCartItem> listShoppingCartItems(long userId);
}
