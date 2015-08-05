package com.chunkr.compress.expressions.operations.unary;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.chunkr.compress.expressions.Evaluable;

@RunWith(MockitoJUnitRunner.class)
public class AbsTest {

	@Mock
	private Evaluable _e1;
	
	@Test
	public void testEvalNegative() {
		Mockito.when(_e1.eval()).thenReturn(BigDecimal.valueOf(-1));
		assertEquals(BigDecimal.valueOf(+1), new Abs(_e1).eval());
		Mockito.verify(_e1).eval();
	}
	
	@Test
	public void testEvalZero() {
		Mockito.when(_e1.eval()).thenReturn(BigDecimal.valueOf(0));
		assertEquals(BigDecimal.valueOf(0), new Abs(_e1).eval());
		Mockito.verify(_e1).eval();
	}
	
	@Test
	public void testEvalPositive() {
		Mockito.when(_e1.eval()).thenReturn(BigDecimal.valueOf(+1));
		assertEquals(BigDecimal.valueOf(+1), new Abs(_e1).eval());
		Mockito.verify(_e1).eval();
	}
	
}
