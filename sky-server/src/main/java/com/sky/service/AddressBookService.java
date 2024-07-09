package com.sky.service;

import com.sky.entity.AddressItem;

import java.util.List;

public interface AddressBookService {

    void addAddress(AddressItem addressItem);

    List<AddressItem> getAddressItemList();
}
