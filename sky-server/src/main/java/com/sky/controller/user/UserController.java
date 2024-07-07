package com.sky.controller.user;

import com.sky.dto.UserLoginDTO;
import com.sky.mapper.UserMapper;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/user/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result Login(@RequestBody UserLoginDTO userLoginDTO){
        UserLoginVO userLoginVO = userService.login(userLoginDTO.getCode());
        return Result.success(userLoginVO);
    }

}
