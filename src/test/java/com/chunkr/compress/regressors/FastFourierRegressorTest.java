package com.chunkr.compress.regressors;

import java.math.BigDecimal;

import org.junit.Test;

public class FastFourierRegressorTest {

	@Test
	public void testFit() {
		int[] chunks = new int[] { 408, 89, -66, 10, 338, 807, 1238, 1511, 1583, 1462, 1183, 804};
		FastFourierRegressor regressor = new FastFourierRegressor();
		System.out.println(regressor.fit(chunks).eval(BigDecimal.valueOf(3)));
	}
}
