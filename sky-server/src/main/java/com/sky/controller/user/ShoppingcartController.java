package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.result.Result;
import com.sky.service.ShoppingcartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/shoppingCart")
public class ShoppingcartController {


    @Autowired
    ShoppingcartService shoppingcartService;

    @PostMapping("/add")
    public Result addGoodToShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shoppingcartService.insertGoodToShoppingCart(shoppingCartDTO);
        return Result.success();
    }
}
