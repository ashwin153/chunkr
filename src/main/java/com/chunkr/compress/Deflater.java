package com.chunkr.compress;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.chunkr.compress.chunkers.Chunker;
import com.chunkr.compress.chunkers.ModifiedChunker;
import com.chunkr.compress.chunkers.StandardChunker;
import com.chunkr.compress.encoders.Encoder;
import com.chunkr.compress.evaluators.Evaluator;
import com.chunkr.compress.regressors.Regressor;
import com.chunkr.expressions.Expression;

public class Deflater implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(Deflater.class);

	private Regressor _regressor;
	private Encoder _encoder;
	private Evaluator _evaluator;
	
	private InputStream _input;
	private OutputStream _output;
	private int _chunkSize;
	
	public Deflater(int chunkSize, InputStream input, OutputStream output,
			Regressor regressor, Encoder encoder, Evaluator evaluator) {
		
		_input = input;
		_output = output;
		
		_chunkSize = chunkSize;
		_regressor = regressor;
		_encoder = encoder;
		_evaluator = evaluator;
	}
	
	@Override
	public void run() {
		try {
			// Step 1: Chunk the input bits using modified chunking
			Chunker standard = new StandardChunker(8);
			Chunker modified = new ModifiedChunker(_chunkSize);
			
			int[] data = new int[_input.available()];
			for(int i = 0; i < data.length; i++)
				data[i] = _input.read();
			
			boolean[] bits = standard.unchunk(data);
			int[] chunks = modified.chunk(bits);

			// Step 2: Regress into expression and create archive
			Expression expression = _regressor.fit(chunks);
			Archive archive = new Archive(modified, expression, chunks.length);
	
			// Step 3: Encode the archive into the specified output stream
			_encoder.write(archive, _output);	
			LOGGER.info("Successfully wrote deflated bits to output stream");
		} catch(Exception e) {
			// Catch any exceptions and print a detailed error message + stack trace
			LOGGER.error("Unable to deflate input data");
			LOGGER.debug(e);
		}
	}
	
}
