package com.taco.loco.orderingservice.service;

import java.util.List;
import java.util.Map;

import com.taco.loco.orderingservice.domain.Menu;
import com.taco.loco.orderingservice.domain.OrderDetails;
import com.taco.loco.orderingservice.domain.OrderTotal;

public interface TotalCalculatorService {

	public OrderTotal caculateTotal(List<OrderDetails> orderList, Map<Long, Menu> menu);

}
