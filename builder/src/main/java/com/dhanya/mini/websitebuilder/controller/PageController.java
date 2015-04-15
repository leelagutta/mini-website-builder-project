package com.dhanya.mini.websitebuilder.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.leela.mini.weeblycommonlib.cache.WeeblyCacheKey;
import com.leela.mini.weeblycommonlib.cache.WeeblyRedisCache;
import com.leela.mini.weeblycommonlib.dao.PageDao;
import com.leela.mini.weeblycommonlib.domain.PageDomain;
import com.leela.mini.weeblycommonlib.exception.ResourceNotFoundException;
import com.leela.mini.weeblycommonlib.model.ElementModel;
import com.leela.mini.weeblycommonlib.model.PageModel;
import com.leela.mini.weeblycommonlib.utils.DomainModelConverter;
import com.leela.mini.weeblycommonlib.utils.JsonUtils;
/**
 * Page controller for weebly-builder. Provider API for Weebly clients the ability to create,
 * update, and delete pages.

 * @author Leela
 */
@Controller
@RequestMapping("/page")
public class PageController {

	private PageDao pageDao;

	private WeeblyRedisCache weeblyRedisCache;
	
	private static final Logger log = Logger.getLogger(PageController.class.getName());
	
	@Autowired
	public PageController(PageDao pageDao, WeeblyRedisCache weeblyRedisCache) {
		this.pageDao = pageDao;
		this.weeblyRedisCache = weeblyRedisCache;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody PageModel createPage(
			@RequestBody @Valid PageModel pageModel, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				log.info(error.getDefaultMessage());
			}
			log.info("Error while creating page");
		}

		PageDomain pageDomain = new PageDomain();
		pageDomain.setName(pageModel.getName());
		PageDomain createdPageDomain = pageDao.create(pageDomain);
		return DomainModelConverter.converToModel(createdPageDomain);
	}
	
	@RequestMapping(value="/{uuid}",method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getPage(@PathVariable String uuid) {	
         String cachedJsonValue = weeblyRedisCache.getInstance().get(WeeblyCacheKey.USER_PAGE + uuid);  
         if(!WeeblyRedisCache.isEmptyCacheValue(cachedJsonValue)) {
        	 return cachedJsonValue;
         }
         
         PageDomain pageDomain = pageDao.getPage(uuid);
         if(pageDomain == null) {
        	 throw new ResourceNotFoundException("Page not found for uuuid: " + uuid);
         }
         
         PageModel pageModel = DomainModelConverter.converToModel(pageDomain);
         String jsonPageValue = JsonUtils.toJson(pageModel);
         weeblyRedisCache.getInstance().set(WeeblyCacheKey.USER_PAGE, jsonPageValue);
         return jsonPageValue;
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

	@RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void deletePage(@PathVariable String uuid) {
		pageDao.delete(uuid);
        weeblyRedisCache.getInstance().decr(WeeblyCacheKey.USER_PAGE + uuid);
	}

	@RequestMapping(value = "/{uuid}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String updatePage(@PathVariable String uuid,@RequestBody @Valid PageModel pageModel) {

		PageDomain pageDomain = new PageDomain();
		pageDomain.setName(pageModel.getName());
		pageDomain.setUuid(uuid);
		pageDomain.setElementDomainListFromElementModelList(pageModel.getElementModelList());

		PageDomain newPageDomain = pageDao.update(uuid, pageDomain);
		PageModel newPageModel = new PageModel();
		newPageModel.setName(newPageDomain.getName());
		newPageModel.setUuid(newPageDomain.getUuid());
		if(!CollectionUtils.isEmpty(newPageDomain.getElementDomainList())) {
			newPageModel.setElementModelList(DomainModelConverter.covertToModel(newPageDomain.getElementDomainList()));
		}
		
		String updatedValues = JsonUtils.toJson(newPageModel);
		weeblyRedisCache.getInstance().set(WeeblyCacheKey.USER_PAGE + uuid, updatedValues);
		return updatedValues;
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