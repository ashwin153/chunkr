package com.chunkr.compress;

import com.google.caliper.Benchmark;
import com.google.caliper.api.VmOptions;

/**
 * This performance benchmark tests the entire compression algorithm from
 * beginning to end. This benchmark is perhaps the most important of all,
 * because it represents the performance costs to end users.
 * 
 * @author ashwin
 */
@VmOptions("-server")
public class End2EndBenchmark {

	@Benchmark
	public void timeDeflate(int reps) {
		
	}
	
	@Benchmark
	public void timeInflate(int reps) {
		
	}
	
}
