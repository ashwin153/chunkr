package com.chunkr.expressions.operations.unary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class PowTest {
	
	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(+4), new Pow(2).eval(BigDecimal.valueOf(+2)));
		assertEquals(BigDecimal.valueOf(+4), new Pow(2).eval(BigDecimal.valueOf(-2)));
	}
	
}
