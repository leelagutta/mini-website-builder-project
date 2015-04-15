package com.dhanya.mini.commonlib.dao;

import java.util.List;

import com.dhanya.mini.commonlib.domain.PageDomain;

/**
 * DAO for page table.
 * 
 * @author Leela
 */
public interface PageDao {
	
    PageDomain create(PageDomain pageDomain);
	
	boolean delete(String uniqueId);
	
	PageDomain update(String uniqueId,PageDomain pageDomain);
	
	List<PageDomain> getpages();
	
	PageDomain getPage(String uniqueId);
}