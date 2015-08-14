package com.chunkr.compress.expressions.operations.binary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class MulTest {
	
	@Test
	public void testArity() {
		assertEquals(2, new Mul().arity());
	}
	
	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(+6), new Mul().eval(BigDecimal.valueOf(+2), BigDecimal.valueOf(+3)));
		assertEquals(BigDecimal.valueOf(-6), new Mul().eval(BigDecimal.valueOf(+2), BigDecimal.valueOf(-3)));
		assertEquals(BigDecimal.valueOf(+6), new Mul().eval(BigDecimal.valueOf(-2), BigDecimal.valueOf(-3)));
	}
	
}
