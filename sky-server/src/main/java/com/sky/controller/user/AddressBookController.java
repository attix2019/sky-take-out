package com.sky.controller.user;

import com.sky.dto.AddressIdDTO;
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

    @PutMapping("/default")
    @ApiOperation("设置某地址为默认地址")
    public Result setAddressItemASDefault(@RequestBody AddressIdDTO addressIdDTO){
        addressBookService.setAddressItemAsDefault(addressIdDTO.getId());
        return Result.success();
    }


    @GetMapping("/default")
    @ApiOperation("获取默认地址")
    public Result getDefaultAddresItem(){
        return Result.success(addressBookService.getDefaultAddressItem());
    }

    @PutMapping
    @ApiOperation("编辑地址项")
    public Result updateAddressItem(@RequestBody AddressItem addressItem){
        addressBookService.updateAddresItem(addressItem);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址项")
    public Result getAddressItemById(@PathVariable long id){
        return Result.success(addressBookService.getAddressItemById(id));
    }

    @DeleteMapping
    @ApiOperation("根据id删除地址项")
    public Result deleteAddressItemById(@RequestBody AddressIdDTO addressIdDTO){
        addressBookService.deleteAddressItemById(addressIdDTO.getId());
        return Result.success();
    }

}
