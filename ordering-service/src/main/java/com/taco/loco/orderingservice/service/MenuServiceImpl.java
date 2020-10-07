package com.taco.loco.orderingservice.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taco.loco.orderingservice.domain.Menu;
import com.taco.loco.orderingservice.repository.MenuDao;

@Service
public class MenuServiceImpl implements MenuService {

	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);
	@Autowired
	private MenuDao menuDaoImpl;

	/**
	 * Service to fetch menu from the backend database and send a map of menu
	 * 
	 * @param
	 * @return Map<Long, Menu>
	 */
	public Map<Long, Menu> fetchMenu() {
		logger.info("fetching menu details from h2 database");
		List<Menu> menuList = menuDaoImpl.getMenuDetails();
		Map<Long, Menu> map = menuList.stream().collect(Collectors.toMap(Menu::getId, menu -> menu));
		return map;
	}

}
