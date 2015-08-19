package com.chunkr.expressions.operations.unary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.chunkr.expressions.operations.unary.Abs;

public class AbsTest {
	
	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(+1), new Abs().eval(BigDecimal.valueOf(+1)));
		assertEquals(BigDecimal.valueOf(+1), new Abs().eval(BigDecimal.valueOf(-1)));
	}
	
}
