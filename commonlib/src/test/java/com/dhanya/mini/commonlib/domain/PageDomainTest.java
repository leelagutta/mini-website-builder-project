package com.dhanya.mini.commonlib.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Page Domain object tests.
 * 
 * @author Leela
 */
public class PageDomainTest {

	@Test
	public void pageDomainCreationTest() {
		PageDomain pageDomainTest = new PageDomain();
		pageDomainTest.setName("homepage");
		pageDomainTest.setUuid("dsaasfasf");
		assertEquals("equal",  pageDomainTest.getName(),"homepage");
		assertEquals("equal",  pageDomainTest.getUuid(),"dsaasfasf");
	}
	
	@Test
	public void pageElementCreationTest() {
		PageDomain pageDomain = new PageDomain();
		ArrayList<ElementDomain> elementDomainList =new ArrayList<ElementDomain>();
		ElementDomain elementDomain;
		for(int i=0;i<2;i++) {
			elementDomain = new ElementDomain();
			elementDomain.setContent("casfasdvsda");
			elementDomain.setType("text");
			elementDomain.setXcord("232");
			elementDomain.setYcord("323");
			elementDomainList.add(elementDomain);
		}
		
		pageDomain.setElementDomainList(elementDomainList);
		for(int i=0;i<pageDomain.getElementDomainList().size();i++) {		
			assertEquals("equal",pageDomain.getElementDomainList().get(i).getContent(),"casfasdvsda");
			assertEquals("equal",pageDomain.getElementDomainList().get(i).getType(),"text");
			assertEquals("equal",pageDomain.getElementDomainList().get(i).getXcord(),"232");
			assertEquals("equal",pageDomain.getElementDomainList().get(i).getYcord(),"323");
		}
	}
}
		

