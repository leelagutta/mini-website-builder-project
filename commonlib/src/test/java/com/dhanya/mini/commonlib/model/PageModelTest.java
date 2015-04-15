package com.dhanya.mini.commonlib.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dhanya.mini.commonlib.domain.ElementDomain;
import com.dhanya.mini.commonlib.utils.DomainModelConverter;

/**
 * Page model tests.
 * 
 * @author Leela
 */
public class PageModelTest {

	@Test
	public void PageModelCreationTest() {
		PageModel pageModelTest = new PageModel();
		pageModelTest.setName("homepage");
		pageModelTest.setUuid("dsaasfasf");

		assertEquals("equal", pageModelTest.getName(), "homepage");
		assertEquals("equal", pageModelTest.getUuid(), "dsaasfasf");
	}

	@Test
	public void pageElementCreationTest() {
		PageModel pageModel = new PageModel();
		List<ElementDomain> elementDomainList = new ArrayList<ElementDomain>();
		ElementDomain elementDomain;

		for (int i = 0; i < 2; i++) {
			elementDomain = new ElementDomain();
			elementDomain.setContent("casfasdvsda");
			elementDomain.setType("text");
			elementDomain.setXcord("232");
			elementDomain.setYcord("323");
			elementDomainList.add(elementDomain);
		}

		pageModel.setElementModelList(DomainModelConverter
				.covertToModel(elementDomainList));
		for (int i = 0; i < pageModel.getElementModelList().size(); i++) {
			assertEquals("equal", pageModel.getElementModelList().get(i)
					.getContent(), "casfasdvsda");
			assertEquals("equal", pageModel.getElementModelList().get(i)
					.getType(), "text");
			assertEquals("equal", pageModel.getElementModelList().get(i)
					.getXcord(), "232");
			assertEquals("equal", pageModel.getElementModelList().get(i)
					.getYcord(), "323");
		}
	}
}