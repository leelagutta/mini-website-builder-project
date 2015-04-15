package com.dhanya.mini.commonlib.model;

import java.util.List;

/**
 * Page model object that is returned to the client
 *
 * @author Leela
 */
public class PageModel {
	
	private String name;
	
	private String uuid;
	
	private List<ElementModel> elementModelList;
	
	public PageModel() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<ElementModel> getElementModelList() {
		return elementModelList;
	}

	public void setElementModelList(List<ElementModel> elementModelList) {
		this.elementModelList = elementModelList;
	}
}