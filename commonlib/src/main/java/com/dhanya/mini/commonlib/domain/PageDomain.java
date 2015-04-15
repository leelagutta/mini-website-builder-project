package com.dhanya.mini.commonlib.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.dhanya.mini.commonlib.model.ElementModel;

/**
 * PageDomain object that will be persisted in to the database
 * 
 * @author Leela
 */
public class PageDomain {
	
	private int id;
	
	private String name;
	
	private String uuid;
	
	private List<ElementDomain> elementDomainList;
	
	public PageDomain(){
		elementDomainList = new ArrayList<ElementDomain>();
	}
	
	public int getId() {
		return id;
	}
	
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
	
	public List<ElementDomain> getElementDomainList() {
		return elementDomainList;
	}
	
	public void setElementDomainListFromElementModelList(List<ElementModel> elementModelList) {	
		if(!CollectionUtils.isEmpty(elementModelList)){		
			for(int i=0;i<elementModelList.size();i++){
				ElementDomain elementDomain = new ElementDomain();
				elementDomain.setContent(elementModelList.get(i).getElementUniqueId());
				elementDomain.setType(elementModelList.get(i).getType());
				elementDomain.setContent(elementModelList.get(i).getContent());
				elementDomain.setXcord(elementModelList.get(i).getXcord());
				elementDomain.setYcord(elementModelList.get(i).getYcord());
				elementDomainList.add(elementDomain);
			}	
		}
	}
	
	public void setElementDomainList(List<ElementDomain> elementDomainList) {
		if(!CollectionUtils.isEmpty(elementDomainList)) {
		    for(int i=0;i<elementDomainList.size();i++){
				this.elementDomainList.add(elementDomainList.get(i));
		    }
		}
	}
	
	public void addingSingleElementDomainList(ElementDomain elementDomain){
		elementDomainList.add(elementDomain);
	}
}