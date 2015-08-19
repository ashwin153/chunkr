package com.chunkr.expressions.operations.nullary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.chunkr.expressions.operations.nullary.Constant;

public class ConstantTest {
	
	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(-1.0), new Constant(BigDecimal.valueOf(-1.0)).eval());
		assertEquals(BigDecimal.valueOf(+1.0), new Constant(BigDecimal.valueOf(+1.0)).eval());
	}
	
}
