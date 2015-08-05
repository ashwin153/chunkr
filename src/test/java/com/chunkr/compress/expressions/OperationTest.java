package com.chunkr.compress.expressions;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OperationTest {

	@Mock
	private Evaluable _e1, _e2;
	
	@Test
	@SuppressWarnings("serial")
	public void testArity() {
		Operation o1 = Mockito.spy(new Operation(_e1) {
			@Override
			public BigDecimal eval() {
				return null;
			}
		});
		
		Operation o2 = Mockito.spy(new Operation(_e1, _e2) {
			@Override
			public BigDecimal eval() {
				return null;
			}
		});
		
		assertEquals(1, o1.arity());
		assertEquals(2, o2.arity());
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testGetAndSet() {
		Mockito.when(_e1.eval()).thenReturn(BigDecimal.valueOf(1));
		Mockito.when(_e2.eval()).thenReturn(BigDecimal.valueOf(0));
		
		Operation o1 = Mockito.spy(new Operation(_e1) {
			@Override
			public BigDecimal eval() {
				return null;
			}
		});
		
		assertEquals(BigDecimal.valueOf(1), o1.get(0).eval());
		Mockito.verify(_e1).eval();
		o1.set(0, _e2);
		assertEquals(BigDecimal.valueOf(0), o1.get(0).eval());
		Mockito.verify(_e2).eval();
	}
	
}
