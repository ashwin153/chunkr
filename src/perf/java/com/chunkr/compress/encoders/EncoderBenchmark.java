package com.chunkr.compress.encoders;
import java.io.InputStream;
import java.io.OutputStream;

import com.chunkr.compress.Archive;
import com.chunkr.compress.encoders.KryoEncoder;
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

	private KryoEncoder _encoder;
	private Archive _archive;
	private InputStream _input;
	private OutputStream _output;
	
	@BeforeExperiment
	public void setUp() throws Exception {
		_encoder = new KryoEncoder();
	}
	
	@Benchmark
	public void timeRead(int reps) {
		for(int i = 0; i < reps; i++)
			_encoder.read(_input);
	}
	
	@Benchmark
	public void timeWrite(int reps) {
		for(int i = 0; i < reps; i++)
			_encoder.write(_archive, _output);
	}
	
}
