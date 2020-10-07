package com.taco.loco.orderingservice.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import com.taco.loco.orderingservice.entity.Menu;

@Repository
public class MenuDaoImpl implements MenuDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<com.taco.loco.orderingservice.domain.Menu> getMenuDetails() {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Menu.class);
		List<Menu> menuEntity = criteria.list();
		List<com.taco.loco.orderingservice.domain.Menu> menuDto = menuEntity.stream()
				.map(menu -> new ModelMapper().map(menu, com.taco.loco.orderingservice.domain.Menu.class))
				.collect(Collectors.toList());
		return menuDto;
	}

}
