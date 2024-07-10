package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.AddressItem;
import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import com.sky.entity.ShoppingCartItem;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.mapper.AddressBookMapper;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    AddressBookMapper addressBookMapper;

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Override
    @Transactional
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {
        // 验证
        AddressItem addressItem = addressBookMapper.getAddressItemById(ordersSubmitDTO.getAddressBookId());
        if(addressItem == null){
            throw  new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }
        long userId = BaseContext.getCurrentId();
        List<ShoppingCartItem> items =  shoppingCartMapper.listShoppingCartItems(userId);
        if(items.size() == 0){
            throw new OrderBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        // 插入
        Orders order = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, order);

        order.setStatus(1); // 默认1待付款
        order.setUserId(BaseContext.getCurrentId());
        LocalDateTime now = LocalDateTime.now();
        order.setOrderTime(now);
        order.setPayStatus(0);

        order.setAddress(addressItem.getDetail());
        order.setPhone(addressItem.getPhone());
        order.setConsignee(addressItem.getConsignee());

        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        order.setNumber(BaseContext.getCurrentId() + "#" + now.format(ofPattern));

        orderMapper.insertOrder(order);

       // 插入明细
        List<OrderDetail> orderDetails = new LinkedList<>();
        for(ShoppingCartItem shoppingCartItem : items){
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(shoppingCartItem, orderDetail);
            orderDetail.setOrderId(order.getId());
            orderDetails.add(orderDetail);
        }
        orderDetailMapper.insertOrderDetails(orderDetails);

        //清空购物车
        shoppingCartMapper.clearShoppingCart(BaseContext.getCurrentId());

        // 返回vo
        return OrderSubmitVO.builder()
                .id(order.getId())
                .orderNumber(order.getNumber())
                .orderAmount(order.getAmount())
                .orderTime(order.getOrderTime())
                .build();
    }

    @Override
    public void pay(OrdersPaymentDTO ordersPaymentDTO) {
        orderMapper.updateOrderStatusAndPayStatus(ordersPaymentDTO.getOrderNumber(), 2, 1);
    }

    @Override
    public PageResult pageQueryHistoryOrders(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(),ordersPageQueryDTO.getPageSize());
        Page<OrderVO> orderVOs = orderMapper.pageQueryHistoryOrders(ordersPageQueryDTO);
        for( OrderVO orderVO : orderVOs){
            // 查出明细
            List<OrderDetail> orderDetails = orderDetailMapper.getOrderDetailByOrderId(orderVO.getId());
            orderVO.setOrderDetailList(orderDetails);
        }
        return new PageResult(orderVOs.getTotal(), orderVOs.getResult());
    }

    @Override
    public OrderVO getOrderContentById(long id) {
        OrderVO orderVO = orderMapper.getOrderById(id);
        orderVO.setOrderDetailList( orderDetailMapper.getOrderDetailByOrderId(id));
        return orderVO;
    }

    @Override
    public void repeatOrder(long id) {
        List<OrderDetail> orderDetails =  orderDetailMapper.getOrderDetailByOrderId(id);
        for(OrderDetail orderDetail : orderDetails ){
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            BeanUtils.copyProperties(orderDetail, shoppingCartItem);
            shoppingCartItem.setCreateTime(LocalDateTime.now());
            shoppingCartItem.setUserId(BaseContext.getCurrentId());
            shoppingCartMapper.insertItem(shoppingCartItem);
        }
    }

    @Override
    public void cancelOrder(long id) {
        /*
        - 待支付和待接单状态下，用户可直接取消订单
        - 商家已接单状态下，用户取消订单需电话沟通商家;派送中状态下，用户取消订单需电话沟通商家
        - 如果在待接单状态下取消订单，需要给用户退款
        - 取消订单后需要将订单状态修改为“已取消”
        * */
        Orders order = orderMapper.getOrderById(id);
        if(order == null){
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        int status = order.getStatus();
        if(status == Orders.COMPLETED || status == Orders.CANCELLED){
            throw  new OrderBusinessException("订单状态异常，无法取消");
        }
        if(status == Orders.CONFIRMED || status == Orders.DELIVERY_IN_PROGRESS){
            throw new OrderBusinessException("取消订单事宜请与商家电话沟通");
        }
        if(status == Orders.TO_BE_CONFIRMED){
            // TODO 真正接入支付以后需要调用退款的api
        }
        order.setStatus(Orders.CANCELLED);
        order.setCancelReason("用户取消订单");
        order.setCancelTime(LocalDateTime.now());
        orderMapper.updateOrder(order);
    }

    @Override
    public OrderStatisticsVO getOrderStatistics() {
        return orderMapper.getOrderStatistics();
    }
}



/*


    //结账时间
    =private LocalDateTime checkoutTime;

    //用户名
    =private String userName;

    //订单取消原因
   =private String cancelReason;

    //订单拒绝原因
    =private String rejectionReason;

    //订单取消时间
    =private LocalDateTime cancelTime;

    //送达时间
    =private LocalDateTime deliveryTime;

*/