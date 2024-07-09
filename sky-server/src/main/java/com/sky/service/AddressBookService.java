package com.sky.service;

import com.sky.entity.AddressItem;

import java.util.List;

public interface AddressBookService {

    void addAddress(AddressItem addressItem);

    List<AddressItem> getAddressItemList();

    AddressItem getDefaultAddressItem();

    void setAddressItemAsDefault(long addressId);

    void updateAddresItem(AddressItem addressItem);

    AddressItem getAddressItemById(long id);

    void deleteAddressItemById(long id);
}
