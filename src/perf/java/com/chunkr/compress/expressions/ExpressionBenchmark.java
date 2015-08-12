package com.chunkr.compress.expressions;

import com.google.caliper.Benchmark;
import com.google.caliper.api.VmOptions;

/**
 * This performance benchmark measures the runtime performance and memory
 * allocation of expression evaluation. Expression evaluation is a significant
 * performance bottleneck; therefore, expressions should be optimized for
 * runtime performance over space to minimize decompression costs.
 * 
 * @author ashwin
 * 
 */
@VmOptions("-server")
public class ExpressionBenchmark {

	@Benchmark
	public void timeEval(int reps) {
		
	}
	
}
