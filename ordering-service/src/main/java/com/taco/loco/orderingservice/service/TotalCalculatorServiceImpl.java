package com.taco.loco.orderingservice.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taco.loco.orderingservice.domain.Menu;
import com.taco.loco.orderingservice.domain.OrderDetails;
import com.taco.loco.orderingservice.domain.OrderTotal;

@Service
public class TotalCalculatorServiceImpl implements TotalCalculatorService {

	private static final Logger logger = LoggerFactory.getLogger(TotalCalculatorServiceImpl.class);

	@Value("${numberOfTacosForDiscount}")
	private int numberOfTacosForDiscount;

	@Value("${discounctPercent}")
	private Double discounctPercent;

	/**
	 * Service to calculate total for Taco orders
	 * 
	 * @param
	 * @return Map<Long, Menu>
	 */
	public OrderTotal caculateTotal(List<OrderDetails> orderList, Map<Long, Menu> menu) {
		Double orderTotal = 0.00;
		int numberOfTacos = 0;
		OrderTotal total = new OrderTotal();
		for (OrderDetails order : orderList) {
			if (null == menu.get(order.getMenuId())) {
				total.getErrorList()
						.add(new StringBuilder("Menu ").append(null == order.getMenuId() ? "null" : order.getMenuId())
								.append(" is not available and skipped in the total").toString());
			} else {
				orderTotal += menu.get(order.getMenuId()).getPrice() * order.getQuantity();
				numberOfTacos += order.getQuantity();
				if (order.getQuantity() == 0) {
					total.getErrorList().add("Quanity is 0 for the menu id " + order.getMenuId());
				}
			}
		}
		total.setOrderTotal(
				numberOfTacos >= numberOfTacosForDiscount ? ((100 - discounctPercent) / 100) * orderTotal : orderTotal);
		logger.info("Total for the order after applying applicable discount is: " + total.getOrderTotal());
		return total;
	}

}
