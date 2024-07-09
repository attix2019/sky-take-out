package com.sky.mapper;

import com.sky.entity.AddressItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    @Insert("insert into address_book" +
            "        (user_id, consignee, phone, sex, province_code, province_name, city_code, city_name, district_code," +
            "         district_name, detail, label, is_default)" +
            "        values (#{userId}, #{consignee}, #{phone}, #{sex}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}," +
            "                #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void insertAddress(AddressItem addressItem);


    @Select("select * from address_book where user_id= #{userId}")
    List<AddressItem> getAddressItemListByUserId(Long userId);


    @Update("update address_book set is_default= #{isDefault} where id = #{id}")
    void setAddressItemAsDefaultValue(long id, int isDefault);

    @Select("select * from address_book where user_id = #{userId} and is_default=1")
    AddressItem getDefaultAddressItemByUserId(long userId);

}
