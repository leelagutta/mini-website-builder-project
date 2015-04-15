package com.dhanya.mini.commonlib.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.dhanya.mini.commonlib.domain.ElementDomain;
import com.dhanya.mini.commonlib.domain.PageDomain;
import com.dhanya.mini.commonlib.model.ElementModel;
import com.dhanya.mini.commonlib.model.PageModel;

/**
 * Helper class for converting elementDomainList to elementModelList and pageDomain to pageModel 
 * 
 * @author Leela
 */
public class DomainModelConverter {

    public static List<ElementModel> covertToModel(List<ElementDomain> elementDomainList) {	
    	if(CollectionUtils.isEmpty(elementDomainList)) {
    		return null;
    	}
    	
    	List<ElementModel> elementModelList = new ArrayList<ElementModel>();
    	if(elementDomainList != null) {
           for(int i=0; i < elementDomainList.size(); i++) {
           		ElementModel elementModel = new ElementModel();
           		elementModel.setElementUniqueId(elementDomainList.get(i).getElementUniqueId());
           		elementModel.setType(elementDomainList.get(i).getType());
           		elementModel.setXcord(elementDomainList.get(i).getXcord());
           		elementModel.setYcord(elementDomainList.get(i).getYcord());
           		elementModel.setContent(elementDomainList.get(i).getContent());
           		elementModelList.add(elementModel);	 
           }
    	}
    	return elementModelList;
	}
  
    public static PageModel converToModel(PageDomain pageDomain) {
    	if(pageDomain == null) {
    		return null;
    	}
        PageModel pageModel = new PageModel();
        pageModel.setName(pageDomain.getName());
        pageModel.setUuid(pageDomain.getUuid());
        pageModel.setElementModelList(covertToModel(pageDomain.getElementDomainList()));
        return pageModel;
    }
}