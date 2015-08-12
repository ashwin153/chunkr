package com.chunkr.compress;

import com.google.caliper.Benchmark;
import com.google.caliper.api.VmOptions;

/**
 * This performance benchmark tests the entire inflation algorithm from
 * beginning to end. This benchmark is perhaps the most important of all,
 * because it represents the performance costs to end users.
 * 
 * @author ashwin
 */
@VmOptions("-server")
public class InflaterBenchmark {
	
	@Benchmark
	public void timeInflate(int reps) {
		
	}
	
}
