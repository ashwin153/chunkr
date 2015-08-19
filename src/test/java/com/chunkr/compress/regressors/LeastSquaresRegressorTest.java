package com.chunkr.compress.regressors;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import com.chunkr.compress.Expression;

public class LeastSquaresRegressorTest {

	@Test
	public void testFit() {
		// Verifies that least squares regression is working correctly. If an
		// nth degree polynomial is fit to a data set of size n, then the
		// polynomial should correctly map every value.
		int[] chunks = new int[] { 1, 6, 3, 5, 9, 2, 4 };
		Expression e1 = new LeastSquaresRegressor(chunks.length).fit(chunks);
		
		for(int i = 0; i < chunks.length; i++)
			assertEquals(chunks[i], e1.eval(BigDecimal.valueOf(i)).doubleValue(), 0.001);
	}
	
}
