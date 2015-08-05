package com.chunkr.compress;

import com.google.caliper.Param;

public class RegressorBenchmark {
	
	@Param
	private Method _method;
	
	public enum Method {
		
		LEAST_SQUARES {
			@Override
			public Regressor getRegressor() {
				return null;
			}
		},
		
		FOURIER {
			@Override
			public Regressor getRegressor() {
				return null;
			}
		},
		
		GENETIC {
			@Override
			public Regressor getRegressor() {
				return null;
			}
		};
		
		abstract Regressor getRegressor();
	}

}
