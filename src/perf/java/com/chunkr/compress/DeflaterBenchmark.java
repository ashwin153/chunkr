package com.chunkr.compress;

import com.google.caliper.Benchmark;
import com.google.caliper.api.VmOptions;

/**
 * This performance benchmark tests the entire deflater algorithm from beginning
 * to end. This benchmark is important because it represents the full
 * performance and memory costs of decompression.
 * 
 * @author ashwin
 */
@VmOptions("-server")
public class DeflaterBenchmark {

	@Benchmark
	public void timeDeflate(int reps) {
		
	}
	
}
