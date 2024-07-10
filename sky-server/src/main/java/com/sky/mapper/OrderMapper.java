package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {

    void insertOrder(Orders order);

    @Update("update orders set status=#{status} , pay_status=#{payStatus} where number=#{orderNumber}")
    void updateOrderStatusAndPayStatus(String orderNumber, int status, int payStatus);

    Page<OrderVO> pageQueryHistoryOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id=#{id}")
    OrderVO getOrderById(long id);

    void updateOrder(Orders order);

}
