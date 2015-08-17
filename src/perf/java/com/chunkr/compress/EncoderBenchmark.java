package com.chunkr.compress;
import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.api.VmOptions;

/**
 * This performance benchmark tests the runtime performance and memory
 * allocation of each type of encoder/serializer. The results of this benchmark
 * will be used to calculate the data compression rates of the algorithm;
 * therefore, it is imperative to optimize the encoders for space over
 * performance.
 * 
 * @author ashwin
 * 
 */
@VmOptions("-server")
public class EncoderBenchmark {

	private Encoder _encoder;
	private Archive _archive;
	
	@BeforeExperiment
	public void setUp() throws Exception {
		_encoder = new Encoder();
	}
	
	@Benchmark
	public void timeRead(int reps) {
		for(int i = 0; i < reps; i++)
			_encoder.read(stream);
	}
	
	@Benchmark
	public void timeWrite(int reps) {
		for(int i = 0; i < reps; i++)
			_encoder.write(_archive, stream);
	}
	
}
