package com.chunkr.compress;

import com.chunkr.compress.chunkers.Chunker;
import com.chunkr.compress.chunkers.ModifiedChunker;
import com.chunkr.compress.chunkers.StandardChunker;
import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.VmOptions;
import com.google.caliper.runner.CaliperMain;

/**
 * This performance benchmarks tests the runtime performance and memory
 * allocation of each method of chunking at various chunk sizes over a
 * randomized sequence of bits.
 * 
 * @author ashwin
 * 
 */
@VmOptions("-server")
public class ChunkerBenchmark {
	
	public static void main(String[] args) {
		CaliperMain.main(ChunkerBenchmark.class, args);
	}
	
	@Param({ "5", "6", "7" })
	private int _chunkSize;
	
	@Param
	private Method _method;
	
	private Chunker _chunker;
	private boolean[] _bits;
	private int[] _chunks;
	
	public enum Method {
		
		STANDARD {
			@Override
			public Chunker getChunker(int chunkSize) {
				return new StandardChunker(chunkSize);
			}
		},
		
		MODIFIED {
			@Override public Chunker getChunker(int chunkSize) {
				return new ModifiedChunker(chunkSize);
			}
		};
		
		abstract public Chunker getChunker(int chunkSize);
	}
	
	@BeforeExperiment
	public void setUp() throws Exception {
		_chunker = _method.getChunker(_chunkSize);
		_bits = new boolean[_chunkSize * 10000];
		for(int i = 0; i < _bits.length; i++)
			_bits[i] = (Math.random() < 0.50) ? true : false;
		_chunks = _chunker.chunk(_bits);
	}
	
	@Benchmark
	public void timeUnchunk(int reps) {
		for(int i = 0; i < reps; i++)
			_chunker.unchunk(_chunks);
	}
	
	@Benchmark
	public void timeChunk(int reps) {
		for(int i = 0; i < reps; i++)
			_chunker.chunk(_bits);
	}
}
