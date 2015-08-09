package com.chunkr.compress;

import com.chunkr.compress.encoders.KryoEncoder;
import com.google.caliper.Param;
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

	@Param
	private Method _method;
		
	public enum Method {
		
		KRYO {
			@Override
			public Encoder getEncoder() {
				return new KryoEncoder();
			}
		};
		
		abstract public Encoder getEncoder();
	}
	
}
