package com.chunkr.compress.expressions.operations.binary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class AddTest {
	
	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(+0), new Add().eval(BigDecimal.valueOf(+1), BigDecimal.valueOf(-1)));
		assertEquals(BigDecimal.valueOf(+2), new Add().eval(BigDecimal.valueOf(+1), BigDecimal.valueOf(+1)));
		assertEquals(BigDecimal.valueOf(-2), new Add().eval(BigDecimal.valueOf(-1), BigDecimal.valueOf(-1)));
	}
	
}
