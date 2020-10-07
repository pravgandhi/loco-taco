package com.taco.loco.orderingservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taco.loco.orderingservice.domain.Menu;
import com.taco.loco.orderingservice.domain.OrderDetails;
import com.taco.loco.orderingservice.domain.OrderRequest;
import com.taco.loco.orderingservice.domain.OrderTotal;
import com.taco.loco.orderingservice.service.MenuService;
import com.taco.loco.orderingservice.service.TotalCalculatorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/order")
@Api(tags = { "taco-order" }, value = "/", consumes = "application/json", produces = "application/json")
@Validated
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private TotalCalculatorService totalCalculator;
	@Autowired
	private MenuService menuService;
	// Cache menu details in-memory. Can be enhanced with TTL feature using Redis or
	// similar solutions
	private Map<Long, Menu> menu = new HashMap<Long, Menu>();

	/**
	 * Controller to calculate total for orders
	 * 
	 * @param request
	 * @return ResponseEntity<OrderTotal>
	 */
	@PostMapping(value = "/total", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Calculate total for Taco orders", notes = "Calculate total for Taco orders based on selected quantity & apply discounts as needed")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Total Calculated"),
			@ApiResponse(code = 403, message = "Forbidden"),
			@ApiResponse(code = 500, message = "Internal Server Error") })
	public ResponseEntity<OrderTotal> caculateTotal(@Valid @RequestBody OrderRequest request) {
		logger.info("calculate total for the taco orderId: {} ", request.getOrderId());
		List<OrderDetails> detailsList = request.getDetails();
		if (menu.isEmpty()) {
			menu = this.menuService.fetchMenu();
		}
		return new ResponseEntity<>(this.totalCalculator.caculateTotal(detailsList, menu), HttpStatus.OK);
	}

}
