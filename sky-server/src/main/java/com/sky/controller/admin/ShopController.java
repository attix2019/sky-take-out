package com.sky.controller.admin;

import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("shopControllerAdminSide")
@Slf4j
@RequestMapping("/admin/shop")
@Api(tags = "店铺状态相关接口")
public class ShopController {

    @Resource
    RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result setShopStatus(@PathVariable int status){
        redisTemplate.opsForValue().set(StatusConstant.STATUS_KEY, status);
        return Result.success();
    }


    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result getShopStatus(){
        int status = (int)redisTemplate.opsForValue().get(StatusConstant.STATUS_KEY);
        return Result.success(status);
    }
}
