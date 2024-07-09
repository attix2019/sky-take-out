package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {

    void insertOrder(Orders order);

    @Update("update orders set status=#{status} , pay_status=#{payStatus} where number=#{orderNumber}")
    void updateOrderStatusAndPayStatus(String orderNumber, int status, int payStatus);
}
