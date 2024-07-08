package com.sky.mapper;

import com.sky.entity.ShoppingCartItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    void insertItem(ShoppingCartItem shoppingCartItem);

    ShoppingCartItem getExistingItem(ShoppingCartItem shoppingCartItem);

    void updateExistingItem(ShoppingCartItem shoppingCartItem);

    @Select("select * from shopping_cart where user_id = #{userId}")
    List<ShoppingCartItem> listShoppingCartItems(long userId);

    @Delete("delete from shopping_cart where user_id = #{id}")
    void clearShoppingCart(long id);

    @Delete("delete from shopping_cart where id = #{id}")
    void deleteItem(ShoppingCartItem shoppingCartItem);
}
