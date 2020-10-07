package com.taco.loco.orderingservice.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderTotal implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double orderTotal;
	private List<String> errorList = new ArrayList<>();

	public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = new BigDecimal(Double.toString(orderTotal)).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
}
