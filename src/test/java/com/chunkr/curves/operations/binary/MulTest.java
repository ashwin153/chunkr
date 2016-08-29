package com.chunkr.curves.operations.binary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.chunkr.curves.operations.binary.Mul;

public class MulTest {
	
	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(+6), new Mul().eval(BigDecimal.valueOf(+2), BigDecimal.valueOf(+3)));
		assertEquals(BigDecimal.valueOf(-6), new Mul().eval(BigDecimal.valueOf(+2), BigDecimal.valueOf(-3)));
		assertEquals(BigDecimal.valueOf(+6), new Mul().eval(BigDecimal.valueOf(-2), BigDecimal.valueOf(-3)));
	}
	
}
