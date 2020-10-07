package com.taco.loco.orderingservice.repository;

import java.util.List;

import com.taco.loco.orderingservice.domain.Menu;

public interface MenuDao {

	public List<Menu> getMenuDetails();
}
