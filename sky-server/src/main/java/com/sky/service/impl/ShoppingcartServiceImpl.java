package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCartItem;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingcartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingcartServiceImpl implements ShoppingcartService {

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    SetmealMapper setmealMapper;

    @Override
    public void insertGoodToShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        // 判断该用户的该商品是否已经存在，不存在则插入，存在则更新数量
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCartItem);
        shoppingCartItem.setUserId(BaseContext.getCurrentId());
        ShoppingCartItem existingItem = shoppingCartMapper.getExistingItem(shoppingCartItem);
        if(existingItem != null){
            // 更新数量 +1
            int nums = existingItem.getNumber() + 1;
            existingItem.setNumber(nums);
            shoppingCartMapper.updateItem(existingItem);
        }else{
            // 额外插入  name, image, number, amount, createtime
            if(shoppingCartDTO.getDishId() != null){
                Dish dish =  dishMapper.findDishById(shoppingCartDTO.getDishId());
                shoppingCartItem.setImage(dish.getImage());
                shoppingCartItem.setName(dish.getName());
                shoppingCartItem.setAmount(dish.getPrice());
            }else{
                Setmeal setmeal = setmealMapper.getSetmealById(shoppingCartDTO.getSetmealId());
                shoppingCartItem.setImage(setmeal.getImage());
                shoppingCartItem.setName(setmeal.getName());
                shoppingCartItem.setAmount(setmeal.getPrice());
            }
            shoppingCartItem.setNumber(1);
            shoppingCartItem.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insertItem(shoppingCartItem);
        }

    }

    @Override
    public List<ShoppingCartItem> listShoppingCartItems() {
        return shoppingCartMapper.listShoppingCartItems(BaseContext.getCurrentId());
    }

    @Override
    public void clearShoppingCart() {
        shoppingCartMapper.clearShoppingCart(BaseContext.getCurrentId());
    }
}