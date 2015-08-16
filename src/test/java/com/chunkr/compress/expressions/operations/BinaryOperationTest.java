package com.chunkr.compress.expressions.operations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

public class BinaryOperationTest {

	@Test
	public void testArity() {
		BinaryOperation oper = Mockito.mock(BinaryOperation.class, Mockito.CALLS_REAL_METHODS);
		assertEquals(2, oper.arity());
	}
	
}