package com.chunkr.compress.regressors;

import com.chunkr.compress.Regressor;
import com.chunkr.compress.regressors.FastFourierRegressor;
import com.chunkr.compress.regressors.GeneticRegressor;
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
	private int[] _data;
	
	public enum Method {
		
		FAST_FOURIER {
			@Override
			public Regressor getRegressor() {
				return new FastFourierRegressor();
			}
		},
		
		DISCRETE_COSINE {
			@Override
			public Regressor getRegressor() {
				return new DiscreteCosineRegressor();
			}
		},
		
		LEAST_SQUARES {
			@Override
			public Regressor getRegressor() {
				return new LeastSquaresRegressor(50);
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
	
	@Benchmark
	public void timeFit(int reps) {
		for(int i = 0; i < reps; i++)
			_regressor.fit(_data);
	}
}
