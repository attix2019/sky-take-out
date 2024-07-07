package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("shopControllerCustomerSide")
@Slf4j
@RequestMapping("/user/shop")
@Api(tags = "用户端查询店铺状态相关接口")
public class ShopController {

    @Resource
    RedisTemplate redisTemplate;

    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result getShopStatus(){
        int status = (int)redisTemplate.opsForValue().get(StatusConstant.STATUS_KEY);
        return Result.success(status);
    }
}
