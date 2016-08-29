package com.chunkr.curves.operations.unary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.chunkr.curves.operations.unary.Pow;

public class PowTest {
	
	@Test
	public void testEval() {
		assertEquals(BigDecimal.valueOf(+4), new Pow(2).eval(BigDecimal.valueOf(+2)));
		assertEquals(BigDecimal.valueOf(+4), new Pow(2).eval(BigDecimal.valueOf(-2)));
	}
	
	@Test
	public void testEquals() {
		assertEquals(new Pow(2), new Pow(2));
		assertTrue(new Pow(2).hashCode() == new Pow(2).hashCode());
		assertNotEquals(new Pow(1), new Pow(2));
	}
	
}
