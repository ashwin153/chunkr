package com.chunkr.expressions;

import com.chunkr.expressions.Regressor;
import com.chunkr.expressions.regressors.DiscreteCosineRegressor;
import com.chunkr.expressions.regressors.DiscreteFourierRegressor;
import com.chunkr.expressions.regressors.LeastSquaresRegressor;
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
	
	@Param({ "0.50", "0.75", "0.90" })
	private double _accuracy;
	
	@Param
	private Method _method;
	
	private Regressor _regressor;
	private double[] _data;
	
	public enum Method {
		
		DISCRETE_FOURIER {
			@Override
			public Regressor getRegressor() {
				return new DiscreteFourierRegressor();
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
				return new LeastSquaresRegressor(10);
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
