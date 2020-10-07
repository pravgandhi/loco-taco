package com.taco.loco.orderingservice.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taco.loco.orderingservice.OrderingServiceApplication;
import com.taco.loco.orderingservice.domain.Menu;
import com.taco.loco.orderingservice.domain.OrderDetails;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = OrderingServiceApplication.class)
public class TotalCalculatorServiceTest {

	@InjectMocks
	private TotalCalculatorServiceImpl service;

	@Mock
	public MenuService menuService;

	private Map<Long, Menu> map;
	List<OrderDetails> order;

	@Before
	public void before() throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
		map = Arrays
				.asList(new ObjectMapper().readValue(
						new File(MenuServiceTest.class.getResource("/json/menu-list.json").toURI()), Menu[].class))
				.stream().collect(Collectors.toMap(Menu::getId, menu -> menu));
		ReflectionTestUtils.setField(service, "numberOfTacosForDiscount", 4);
		ReflectionTestUtils.setField(service, "discounctPercent", 20.00);
	}

	@Test
	public void calculateTotalWithoutDiscount()
			throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
		order = Arrays.asList(new ObjectMapper().readValue(
				new File(MenuServiceTest.class.getResource("/json/order-without-discount.json").toURI()),
				OrderDetails[].class));
		Assert.assertTrue(this.service.caculateTotal(order, map).getOrderTotal().equals(8.0));
	}

	@Test
	public void calculateTotalWithDiscount()
			throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
		order = Arrays.asList(new ObjectMapper().readValue(
				new File(MenuServiceTest.class.getResource("/json/order-with-discount.json").toURI()),
				OrderDetails[].class));
		Assert.assertTrue(this.service.caculateTotal(order, map).getOrderTotal().equals(13.6));
	}

	@Test
	public void calculateTotalWithDiscountEdgeCase()
			throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
		order = Arrays.asList(new ObjectMapper().readValue(
				new File(MenuServiceTest.class.getResource("/json/order-with-discount-four.json").toURI()),
				OrderDetails[].class));
		Assert.assertTrue(this.service.caculateTotal(order, map).getOrderTotal().equals(9.6));
	}
}
