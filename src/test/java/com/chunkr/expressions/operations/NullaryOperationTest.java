package com.chunkr.expressions.operations;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

import com.chunkr.expressions.operations.NullaryOperation;

public class NullaryOperationTest {

	@Test
	public void testArity() {
		NullaryOperation oper = Mockito.mock(NullaryOperation.class, Mockito.CALLS_REAL_METHODS);
		assertEquals(0, oper.arity());
	}
	
}
