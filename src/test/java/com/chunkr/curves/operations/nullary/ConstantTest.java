package com.chunkr.curves.operations.nullary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.chunkr.curves.operations.nullary.Constant;

import static org.junit.Assert.*;

public class ConstantTest {
	
	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(-1.0), new Constant(-1.0).eval());
		assertEquals(BigDecimal.valueOf(+1.0), new Constant(+1.0).eval());
	}
	
	@Test
	public void testEquals() {
		assertEquals(new Constant(0), new Constant(0.0));
		assertTrue(new Constant(0).hashCode() == new Constant(0.0).hashCode());
		assertNotEquals(new Constant(1), new Constant(-1));
	}
	
}
