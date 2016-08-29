package com.chunkr.curves.regressors;

import org.junit.Test;

import com.chunkr.curves.regressors.GeneticRegressor;
import com.chunkr.genetics.selectors.TournamentSelector;

public class GeneticRegressorTest {

	@Test
	public void testFit() {
		GeneticRegressor regressor = new GeneticRegressor(1000, 100, 0.02,
				0.85, 0.10, new TournamentSelector(10));
		
		double[] points = new double[100];
		for(int i = 0; i < points.length; i++)
			points[i] = Math.pow(i, 0.5);
		
		System.out.println(regressor.fit(points));
	}
	
}
