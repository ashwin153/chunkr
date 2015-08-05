package com.chunkr.compress.expressions.operations.binary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chunkr.compress.expressions.Evaluable;

@RunWith(MockitoJUnitRunner.class)
public class MulTest {

	@Mock
	private Evaluable _e1, _e2;
	
	@Test
	public void testEval() {
		Mockito.when(_e1.eval()).thenReturn(BigDecimal.valueOf(+2));
		Mockito.when(_e2.eval()).thenReturn(BigDecimal.valueOf(-3));
		
		assertEquals(BigDecimal.valueOf(-6), new Mul(_e1, _e2).eval());
		Mockito.verify(_e1).eval();
		Mockito.verify(_e2).eval();
	}
	
}
