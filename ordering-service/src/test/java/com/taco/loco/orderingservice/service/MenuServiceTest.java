package com.taco.loco.orderingservice.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taco.loco.orderingservice.OrderingServiceApplication;
import com.taco.loco.orderingservice.domain.Menu;
import com.taco.loco.orderingservice.repository.MenuDao;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = OrderingServiceApplication.class)
public class MenuServiceTest {

	@InjectMocks
	private MenuServiceImpl service;

	@Mock
	private MenuDao menuDaoImpl;

	@Before
	public void before() throws JsonParseException, JsonMappingException, IOException, URISyntaxException {
		Menu[] menuArray = new ObjectMapper()
				.readValue(new File(MenuServiceTest.class.getResource("/json/menu-list.json").toURI()), Menu[].class);
		List<Menu> menuListMock = Arrays.asList(menuArray);
		when(menuDaoImpl.getMenuDetails()).thenReturn(menuListMock);
	}

	// Test the size of the menu
	@Test
	public void fetchMenuTest() {
		Map<Long, Menu> menuList = this.service.fetchMenu();
		assertTrue(menuList.size() == 4);
	}

	// Do a price check
	@Test
	public void checkForVeggieTaco() {
		Map<Long, Menu> menuList = this.service.fetchMenu();
		for (Long menuId : menuList.keySet()) {
			assertTrue(null != menuList.get(menuId) && menuList.get(menuId).getName().equals("Veggie Taco"));
			break;
		}
		assertTrue(menuList.get(1L).getPrice() == 2.50 && "Veggie Taco".equals(menuList.get(1L).getName()));
	}

}
