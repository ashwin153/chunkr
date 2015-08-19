package com.chunkr.expressions.operations.binary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.chunkr.expressions.operations.binary.Sub;

public class SubTest {
	
	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(+2), new Sub().eval(BigDecimal.valueOf(+1), BigDecimal.valueOf(-1)));
		assertEquals(BigDecimal.valueOf(+0), new Sub().eval(BigDecimal.valueOf(+1), BigDecimal.valueOf(+1)));
		assertEquals(BigDecimal.valueOf(+0), new Sub().eval(BigDecimal.valueOf(-1), BigDecimal.valueOf(-1)));
	}

}
