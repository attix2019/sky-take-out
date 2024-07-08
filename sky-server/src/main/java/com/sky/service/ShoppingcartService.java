package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCartItem;

import java.util.List;

public interface ShoppingcartService {

    void insertGoodToShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCartItem> listShoppingCartItems();

    void clearShoppingCart();
}
