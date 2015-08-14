package com.chunkr.compress.expressions.operations.nullary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ConstantTest {

	@Test
	public void testArity() {
		assertEquals(0, new Constant(BigDecimal.valueOf(0)).arity());
	}
	
	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(-1.0), new Constant(BigDecimal.valueOf(-1.0)).eval());
		assertEquals(BigDecimal.valueOf(+1.0), new Constant(BigDecimal.valueOf(+1.0)).eval());
	}
	
}
