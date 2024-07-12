package com.sky.task;

import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class ScheduledTasks {

    @Autowired
    OrderMapper orderMapper;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void cancelOrderIfNotPaidIn15Minutes(){
        log.info("清理未及时支付的订单");
        orderMapper.cancelOrderIfNotPaidIn15Minutes();
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void setDeliveryInProgressOrdersToCompletedAfterCloseUp(){
        log.info("闭店足够久后将派送中的订单标记为完成");
        orderMapper.setDeliveryInProgressOrdersToCompletedAfterCloseUp();
    }

}
