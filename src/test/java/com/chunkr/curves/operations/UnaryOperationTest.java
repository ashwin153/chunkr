package com.chunkr.curves.operations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

import com.chunkr.curves.operations.UnaryOperation;

public class UnaryOperationTest {

	@Test
	public void testArity() {
		UnaryOperation oper = Mockito.mock(UnaryOperation.class, Mockito.CALLS_REAL_METHODS);
		assertEquals(1, oper.arity());
	}
	
}
