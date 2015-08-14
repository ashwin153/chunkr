package com.chunkr.compress.expressions.operations.unary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class SinTest {

	@Test
	public void testArity() {
		assertEquals(1, new Sin().arity());
	}
	
	@Test
	public void testEval() {
		assertEquals(+0.0, new Sin().eval(BigDecimal.valueOf(0)).doubleValue(), 0.001);
		assertEquals(+1.0, new Sin().eval(BigDecimal.valueOf(Math.PI * 0.5)).doubleValue(), 0.001);
		assertEquals(+0.0, new Sin().eval(BigDecimal.valueOf(Math.PI)).doubleValue(), 0.001);
		assertEquals(-1.0, new Sin().eval(BigDecimal.valueOf(Math.PI * 1.5)).doubleValue(), 0.001);
	}
	
}
