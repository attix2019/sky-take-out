package com.sky.controller.user;

import com.sky.entity.AddressItem;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("用户地址簿相关接口")
@RequestMapping("/user/addressBook")
@Slf4j
public class AddressBookController {

    @Autowired
    AddressBookService addressBookService;

    @PostMapping
    @ApiOperation("新增地址")
    public Result save(@RequestBody AddressItem addressItem) {
        addressBookService.addAddress(addressItem);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查询当前登录用户的所有地址信息")
    public Result<List<AddressItem>> getAddressItemList() {
        return Result.success(addressBookService.getAddressItemList());
    }

}
