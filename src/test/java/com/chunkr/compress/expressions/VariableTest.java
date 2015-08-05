package com.chunkr.compress.expressions;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class VariableTest {

	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(+2), new Variable("x0", BigDecimal.valueOf(+2)));
		assertEquals(BigDecimal.valueOf(-1), new Variable("x1", BigDecimal.valueOf(-1)));
	}
	
	@Test
	public void testSet() {
		Variable var = new Variable("x", BigDecimal.ZERO);
		assertEquals(BigDecimal.ZERO, var.eval());
		
		var.set(BigDecimal.ONE);
		assertEquals(BigDecimal.ONE, var.eval());
	}
	
	@Test
	public void testToString() {
		assertEquals("X", new Variable("X").toString());
		assertEquals("x", new Variable("x").toString());
		assertEquals("1", new Variable("1").toString());
		assertEquals("@", new Variable("@").toString());
	}
	
}
