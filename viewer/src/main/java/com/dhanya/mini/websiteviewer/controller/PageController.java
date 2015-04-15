package com.dhanya.mini.websiteviewer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.leela.mini.weeblycommonlib.cache.WeeblyCacheKey;
import com.leela.mini.weeblycommonlib.cache.WeeblyRedisCache;
import com.leela.mini.weeblycommonlib.dao.PageDao;
import com.leela.mini.weeblycommonlib.domain.PageDomain;
import com.leela.mini.weeblycommonlib.model.ElementModel;
import com.leela.mini.weeblycommonlib.model.PageModel;
import com.leela.mini.weeblycommonlib.utils.DomainModelConverter;
import com.leela.mini.weeblycommonlib.utils.JsonUtils;
/**
 * Controller that handles GET operations for pages.
 * 
 * @author Leela
 */
@Controller
@RequestMapping("/page")
public class PageController {
	
	private PageDao pageDao;
	
	private WeeblyRedisCache weeblyRedisCache;
	
	@Autowired
	public PageController(PageDao pageDao, WeeblyRedisCache weeblyRedisCache) {
		this.pageDao = pageDao;
		this.weeblyRedisCache = weeblyRedisCache;
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getPages() {
		String cachedJsonValue = weeblyRedisCache.getInstance().get(WeeblyCacheKey.USER_PAGES); 
		if(cachedJsonValue != null) {
			return cachedJsonValue;
		}
		
		List<PageModel> pageModels = getPageModels();
		if(CollectionUtils.isEmpty(pageModels)) {
			return null;
		}
        String jsonPageValue = JsonUtils.toJson(pageModels);
        weeblyRedisCache.getInstance().set(WeeblyCacheKey.USER_PAGES, jsonPageValue);
        return jsonPageValue;	 
	}	 
	
	@RequestMapping(value="/{uuid}",method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getPage(@PathVariable String uuid) {	
         String cachedJsonValue = weeblyRedisCache.getInstance().get(WeeblyCacheKey.USER_PAGE + uuid);   
         if(!StringUtils.isEmpty(cachedJsonValue)){
        	 return cachedJsonValue;
         }
         
         PageDomain pageDomain = pageDao.getPage(uuid);
         if(pageDomain == null) {
        	 return null;
         }
         PageModel pageModel = DomainModelConverter.converToModel(pageDomain);
         String jsonPageValue = JsonUtils.toJson(pageModel);
         weeblyRedisCache.getInstance().set(WeeblyCacheKey.USER_PAGE, jsonPageValue);
         return jsonPageValue;
	} 	 
	
	private List<PageModel> getPageModels() {
		List<PageModel> pageModelList = new ArrayList<PageModel>();
		List<PageDomain> pageDomainList = pageDao.getpages();
		for(PageDomain pageDomain: pageDomainList) {
			PageModel pageModel = new PageModel();
			pageModel.setName(pageDomain.getName());
			pageModel.setUuid(pageDomain.getUuid());
			if(!CollectionUtils.isEmpty(pageDomain.getElementDomainList())) {
				List<ElementModel> covertToModel = DomainModelConverter.covertToModel(pageDomain.getElementDomainList());
				pageModel.setElementModelList(covertToModel);
			}
			pageModelList.add(pageModel);
		}
		return pageModelList;
	}
}