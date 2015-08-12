package com.chunkr.compress.expressions.operations.unary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.chunkr.compress.expressions.operations.unary.Neg;

public class NegTest {
	
	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(+1), new Neg().eval(BigDecimal.valueOf(-1)));
		assertEquals(BigDecimal.valueOf(-1), new Neg().eval(BigDecimal.valueOf(+1)));
	}
	
}
