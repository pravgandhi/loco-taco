package com.taco.loco.orderingservice.controller;

import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taco.loco.orderingservice.domain.Menu;
import com.taco.loco.orderingservice.domain.OrderRequest;
import com.taco.loco.orderingservice.domain.OrderTotal;
import com.taco.loco.orderingservice.service.MenuService;
import com.taco.loco.orderingservice.service.MenuServiceTest;
import com.taco.loco.orderingservice.service.TotalCalculatorService;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

	@InjectMocks
	public OrderController controller;
	@Mock
	public MenuService menuService;
	@Mock
	public TotalCalculatorService totalCalculatorService;
	private OrderRequest orderRequest;

	@Before
	public void before() throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
		Map<Long, Menu> map = Arrays
				.asList(new ObjectMapper().readValue(
						new File(MenuServiceTest.class.getResource("/json/menu-list.json").toURI()), Menu[].class))
				.stream().collect(Collectors.toMap(Menu::getId, menu -> menu));
		when(this.menuService.fetchMenu()).thenReturn(map);

	}

	@Test
	public void testGetFileByEDelivery()
			throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
		// this.controller.caculateTotal(request);
		this.orderRequest = new ObjectMapper().readValue(
				new File(OrderControllerTest.class.getResource("/json/happypath.json").toURI()), OrderRequest.class);
		ResponseEntity<OrderTotal> response = this.controller.caculateTotal(orderRequest);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
