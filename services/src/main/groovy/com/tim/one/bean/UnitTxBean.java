package com.tim.one.bean;

import java.util.List;

/**
 * @author josdem
 * @understands A class who contains UnitTx values
 * 
 */

public class UnitTxBean {
	
	private Integer projectId;
	private Integer userId;
	private List<Integer> unitSales;
	private List<Integer> quantities;
	
	public Integer getProjectId() {
		return projectId;
	}
	
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public List<Integer> getUnitSales() {
		return unitSales;
	}
	
	public void setUnitSales(List<Integer> unitSales) {
		this.unitSales = unitSales;
	}
	
	public List<Integer> getQuantities() {
		return quantities;
	}
	
	public void setQuantities(List<Integer> quantities) {
		this.quantities = quantities;
	}

}
