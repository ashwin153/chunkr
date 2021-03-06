package com.chunkr.curves.operations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

import com.chunkr.curves.operations.BinaryOperation;

public class BinaryOperationTest {

	@Test
	public void testArity() {
		BinaryOperation oper = Mockito.mock(BinaryOperation.class, Mockito.CALLS_REAL_METHODS);
		assertEquals(2, oper.arity());
	}
	
}
