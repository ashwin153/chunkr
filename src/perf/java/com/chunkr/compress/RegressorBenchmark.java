package com.chunkr.compress;

import java.awt.geom.Point2D;

import com.chunkr.compress.regressors.FourierRegressor;
import com.chunkr.compress.regressors.GeneticRegressor;
import com.chunkr.compress.regressors.LeastSquaresRegressor;
import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.VmOptions;

/**
 * This performance benchmarks measures the runtime performance and memory
 * allocation of the regressors. Regression is a significant performance
 * bottleneck; therefore, regressors should be optimized for performance over
 * space.
 * 
 * @author ashwin
 * 
 */
@VmOptions("-server")
public class RegressorBenchmark {
	
	@Param
	private Method _method;
	
	private Regressor _regressor;
	private Point2D[] _data;
	
	public enum Method {
		
		LEAST_SQUARES {
			@Override
			public Regressor getRegressor() {
				return new LeastSquaresRegressor();
			}
		},
		
		FOURIER {
			@Override
			public Regressor getRegressor() {
				return new FourierRegressor();
			}
		},
		
		GENETIC {
			@Override
			public Regressor getRegressor() {
				return new GeneticRegressor();
			}
		};
		
		abstract Regressor getRegressor();
	}

	@BeforeExperiment
	public void setUp() throws Exception {
		_regressor = _method.getRegressor();
		_data = new Point2D[1000];
		
		for(int i = 0; i < _data.length; i++)
			_data[i] = new Point2D.Double(i, Math.random() * 32);
	}
	
	@Benchmark
	public void timeFit(int reps) {
		for(int i = 0; i < reps; i++)
			_regressor.fit(_data);
	}
}
