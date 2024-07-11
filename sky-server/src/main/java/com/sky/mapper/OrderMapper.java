package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.core.annotation.Order;

@Mapper
public interface OrderMapper {

    void insertOrder(Orders order);

    @Update("update orders set status=#{status} , pay_status=#{payStatus} where number=#{orderNumber}")
    void updateOrderStatusAndPayStatus(String orderNumber, int status, int payStatus);

    Page<OrderVO> pageQueryHistoryOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    @Select("select * from orders where id=#{id}")
    OrderVO getOrderById(long id);

    void updateOrder(Orders order);

    @Select("select sum(status = 2) toBeConfirmed," +
            "    sum(status = 3) confirmed," +
            "    sum(status = 4) deliveryInProgress " +
            "from orders")
    OrderStatisticsVO getOrderStatistics();


    @Select("select * from orders where number = #{orderNumber}")
    Orders getOrderByOrderNumber(String orderNumber);


}
