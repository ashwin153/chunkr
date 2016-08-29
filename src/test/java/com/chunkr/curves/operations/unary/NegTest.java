package com.chunkr.curves.operations.unary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.chunkr.curves.operations.unary.Neg;

public class NegTest {
	
	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(+1), new Neg().eval(BigDecimal.valueOf(-1)));
		assertEquals(BigDecimal.valueOf(-1), new Neg().eval(BigDecimal.valueOf(+1)));
	}
	
}
