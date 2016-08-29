package com.chunkr.curves.operations.nullary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.chunkr.curves.operations.nullary.Variable;

public class VariableTest {
	
	@Test
	public void testEval() {
		Variable x = new Variable('x', BigDecimal.valueOf(+1.0));
		assertEquals(BigDecimal.valueOf(+1.0), x.eval());
	
		x.setValue(BigDecimal.valueOf(-1.0));
		assertEquals(BigDecimal.valueOf(-1.0), x.eval());
	}
	
}
