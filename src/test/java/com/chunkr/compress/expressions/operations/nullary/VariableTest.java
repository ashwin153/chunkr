package com.chunkr.compress.expressions.operations.nullary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class VariableTest {

	@Test
	public void testArity() {
		assertEquals(0, new Variable("x").arity());
	}
	
	@Test
	public void testEval() {
		Variable x = new Variable("x", BigDecimal.valueOf(+1.0));
		assertEquals(BigDecimal.valueOf(+1.0), x.eval());
	
		x.set(BigDecimal.valueOf(-1.0));
		assertEquals(BigDecimal.valueOf(-1.0), x.eval());
	}
	
}
