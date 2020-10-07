package com.taco.loco.orderingservice.service;

import java.util.Map;

import com.taco.loco.orderingservice.domain.Menu;

public interface MenuService {

	public Map<Long, Menu> fetchMenu();

}
