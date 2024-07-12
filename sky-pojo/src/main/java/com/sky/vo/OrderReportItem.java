package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderReportItem {

    String date;

    Integer orderCount;

    Integer validOrderCount;
}
