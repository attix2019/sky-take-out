package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressItem;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    AddressBookMapper addressBookMapper;

    @Override
    public void addAddress(AddressItem addressItem) {
        addressItem.setUserId(BaseContext.getCurrentId());
        // 如果此前没有添加过地址，是不是应该直接设置成默认的？
        addressItem.setIsDefault(0);
        addressBookMapper.insertAddress(addressItem);
    }

    @Override
    public List<AddressItem> getAddressItemList() {
        return addressBookMapper.getAddressItemListByUserId(BaseContext.getCurrentId());
    }

    @Override
    public AddressItem getDefaultAddressItem() {
        return addressBookMapper.getDefaultAddressItemByUserId(BaseContext.getCurrentId());
    }

    @Override
    @Transactional
    public void setAddressItemAsDefault(long addressId) {
        AddressItem currentDefultAddress = getDefaultAddressItem();
        if(currentDefultAddress != null){
            addressBookMapper.setAddressItemAsDefaultValue(currentDefultAddress.getId(), 0);
        }
        addressBookMapper.setAddressItemAsDefaultValue(addressId, 1);
    }
}
