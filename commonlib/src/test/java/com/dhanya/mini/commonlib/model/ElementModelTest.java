package com.dhanya.mini.commonlib.model;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import com.dhanya.mini.commonlib.domain.ElementDomain;

/**
 * Element model test
 * 
 * @author Leela
 */
public class ElementModelTest {

	@Test
	public void ElementDomainCreationTest() {
		ElementDomain elementDomain = new ElementDomain();
		elementDomain.setContent("casfasdvsda");
		elementDomain.setElementUniqueId((Integer.toString(new Random()
				.nextInt(10))));
		elementDomain.setType("text");
		elementDomain.setXcord("232");
		elementDomain.setYcord("323");

		assertEquals("equal", elementDomain.getContent(), "casfasdvsda");
		assertEquals("equal", elementDomain.getType(), "text");
		assertEquals("equal", elementDomain.getXcord(), "232");
		assertEquals("equal", elementDomain.getYcord(), "323");
	}
}
